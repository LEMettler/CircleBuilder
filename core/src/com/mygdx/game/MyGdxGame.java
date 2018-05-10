package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor{
    final int FIELDX = 4;
    final int FIELDY = 7;

    private int marginX;
    private int marginY;

	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private Circle[][] field;
	private BitmapFont count;

	private int screenWidth, screenHeight;

	private int countIndex, countX, countY;

	
	@Override
	public void create () {


		batch = new SpriteBatch();
        Gdx.input.setInputProcessor(this);
		shapeRenderer = new ShapeRenderer();
		screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();

        marginY = 0;
        marginX = 0;

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
                field[j][i] = new Circle(squareX + squareRadius + marginX,squareY + squareRadius + marginY, squareRadius);
                squareX = squareX + squareSize;
            }
            squareY = squareY + squareSize;
            squareX = 0;
        }
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
                //activateCircle(Gdx.input.getX(),Gdx.input.getY(), field[j][i]);

                if (field[j][i].getActivated()) {
                    shapeRenderer.setColor(Color.RED);
                    shapeRenderer.circle(field[j][i].getX(), screenHeight - field[j][i].getY(), field[j][i].getRadius());

                }else{
                    shapeRenderer.setColor(Color.BLUE);
                    shapeRenderer.circle(field[j][i].getX(), screenHeight - field[j][i].getY(), field[j][i].getRadius());
                }
            }
        }

        batch.begin();
        count.draw(batch,Integer.toString(countIndex),countX,countY);
        batch.end();

        shapeRenderer.end();
	}

        //finger on this circle -> activate(red)
    public void activateCircle(int x, int y, Circle circle){

        x = circle.getX() - x;
        y = circle.getY() - y;

        if (x * x + y * y <= circle.getRadius()* circle.getRadius()){
            if (!circle.getActivated()) {
                countIndex++;
            }
            circle.setActivated(true);
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        for (int i = 0; i < FIELDY; i++) {
            for (int j = 0; j < FIELDX; j++) {
                activateCircle(Gdx.input.getX(), Gdx.input.getY(), field[j][i]);
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
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        for (int i = 0; i < FIELDY; i++) {
            for (int j = 0; j < FIELDX; j++) {
                activateCircle(Gdx.input.getX(), Gdx.input.getY(), field[j][i]);
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
}