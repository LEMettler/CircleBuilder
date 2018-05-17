package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor, GestureDetector.GestureListener{
    final int FIELDX = 4;
    final int FIELDY = 7;

    private int marginX;
    private int marginY;

	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private Circle[][] field;
	private LineSegment[] line;
	private BitmapFont count;

	private int screenWidth, screenHeight;
	private Boolean win;

	private int countIndex, countX, countY;
	private int barriers, freeFields;

	private Circle lastCircle;
	private Circle startCircle;
	private Circle finishCircle;

	
	@Override
	public void create () {

		batch = new SpriteBatch();

        InputMultiplexer im = new InputMultiplexer();
        GestureDetector gd = new GestureDetector(this);
        im.addProcessor(gd);
        im.addProcessor(this);
        Gdx.input.setInputProcessor(im);

		shapeRenderer = new ShapeRenderer();
		screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();

        line = new LineSegment[FIELDX * FIELDY + 1];
        Arrays.fill(line,null);

        marginY = 0;
        marginX = 0;
        win = false;

        lastCircle = null;
        startCircle = null;
        finishCircle = null;

        count = new BitmapFont();
        countIndex = 0;
        countX = 3;
        countY = 325;
        count.setColor(Color.BLACK);
        count.getData().scale(5);
        int squareSize =(screenWidth - marginX)/FIELDX;
        int squareRadius = squareSize/2;
        field = new Circle[FIELDX][FIELDY];

        int squareX = 0;
        int squareY = 0;
        for (int i = 0; i <FIELDY; i++) {
            for (int j = 0; j < FIELDX; j++) {
                field[j][i] = new Circle(squareX + squareRadius + marginX,squareY + squareRadius + marginY, squareRadius,j,i);
                squareX = squareX + squareSize;
            }
            squareY = squareY + squareSize;
            squareX = 0;
        }

        //barriers
        barriers = 4;
        field[1][1].setBlocked(true);
        field[1][2].setBlocked(true);
        field[2][1].setBlocked(true);
        field[2][2].setBlocked(true);

        freeFields = (FIELDX * FIELDY) - barriers;
    }

    @Override
    public void dispose () {
        batch.dispose();
        shapeRenderer.dispose();
        count.dispose();
    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Gdx.gl.glLineWidth(32); //Line thickness
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        for (int i = 0; i < FIELDY; i++) {
            for (int j = 0; j < FIELDX; j++) {

                if (field[j][i].getBlocked()){

                    shapeRenderer.setColor(Color.BLACK);
                    shapeRenderer.circle(field[j][i].getX(), screenHeight - field[j][i].getY(), field[j][i].getRadius());

                } else if (field[j][i].getActivated()) {

                    if (field[j][i].getSpecial())
                        shapeRenderer.setColor(Color.GREEN);
                    else
                        shapeRenderer.setColor(Color.RED);

                    shapeRenderer.circle(field[j][i].getX(), screenHeight - field[j][i].getY(), field[j][i].getRadius());

                }else{
                    shapeRenderer.setColor(Color.BLUE);
                    shapeRenderer.circle(field[j][i].getX(), screenHeight - field[j][i].getY(), field[j][i].getRadius());
                }
            }
        }

        if (win)
        shapeRenderer.setColor(Color.GREEN);
        else
            shapeRenderer.setColor(Color.BLACK);

        for (int i = 0; i < line.length; i++) {
            if (line[i] !=null)
                shapeRenderer.rectLine(line[i].getStart(),line[i].getEnd(),line[i].getWidth());
        }

        shapeRenderer.end();

        batch.begin();
        //count.draw(batch,Integer.toString(countIndex),countX,countY);
        count.draw(batch,Integer.toString(freeFields),countX,countY);
        batch.end();


	}

        //finger on this circle -> activate(red)
        private void activateCircle(int x, int y, Circle circle, Boolean special){

        x = circle.getX() - x;
        y = circle.getY() - y;

        if (!circle.getBlocked()) {
            if (x * x + y * y <= circle.getRadius() * circle.getRadius()) {                               //TODO structure
                if (((lastCircle == null) || lastCircle.isNextTo(circle.getRow(), circle.getColumn())) && !circle.getActivated()) {

                    if (!circle.getActivated()) {
                        countIndex++;
                    }

                    circle.setActivated(true);
                    circle.setSpecial(special);

                    if (lastCircle != null) {
                        Vector2 start = new Vector2(lastCircle.getX(), screenHeight - lastCircle.getY());
                        Vector2 end = new Vector2(circle.getX(), screenHeight - circle.getY());
                        line[countIndex] = new LineSegment(30, start, end);
                    }

                    lastCircle = circle;

                    freeFields--;
                }
            }
        }
    }

    private void blockCircle(int x, int y, Circle circle){
        x = circle.getX() - x;
        y = circle.getY() - y;

            if (x * x + y * y <= circle.getRadius() * circle.getRadius()) {                               //TODO structure

                if (circle.getBlocked()) {
                    circle.setBlocked(false);
                    barriers++;
                }else {
                    circle.setBlocked(true);
                    barriers--;
                }

                freeFields = (FIELDX * FIELDY) - barriers;
                }

        }


    private void checkWin(){
        int startRow = startCircle.getRow();
        int startColumn = startCircle.getColumn();

        if (finishCircle.isNextTo(startRow,startColumn));
            win = true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        for (int i = 0; i < FIELDY; i++) {
            for (int j = 0; j < FIELDX; j++) {
                if (!field[j][i].getBlocked()) {
                    activateCircle(Gdx.input.getX(), Gdx.input.getY(), field[j][i], true);  //todo first circle can be blocked
                    startCircle = field[j][i];
                }
            }
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        for (int i = 0; i < FIELDY; i++) {
            for (int j = 0; j < FIELDX; j++) {
                field[j][i].setActivated(false);
                countIndex = 0;
            }
        }

        Arrays.fill(line,null);
        lastCircle = null;
        freeFields = (FIELDX * FIELDY) - barriers;
        win = false;

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Boolean special = false;

        if (freeFields == 1)
            special = true;

        for (int i = 0; i < FIELDY; i++) {
            for (int j = 0; j < FIELDX; j++) {
                activateCircle(Gdx.input.getX(), Gdx.input.getY(), field[j][i], special);
                if (freeFields == 0) {
                    finishCircle = field[j][i];
                    checkWin();
                }
            }
        }
        return true;
    }


    @Override
    public boolean longPress(float x, float y) {
        for (int i = 0; i < FIELDY; i++) {
            for (int j = 0; j < FIELDX; j++) {
                blockCircle((int) x, (int) y, field[j][i]);
            }
        }
        return true;
    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }



    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}