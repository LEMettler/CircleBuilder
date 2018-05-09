package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MyGdxGame extends ApplicationAdapter implements GestureDetector.GestureListener{
    final int FIELDX = 4;
    final int FIELDY = 6;

    private int marginX;
    private int marginY;

	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private Circle[][] field;

	private int screenWidth, screenHeight;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();

        marginY = 50;
        marginX = 0;
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
    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.end();

        //Gdx.gl.glLineWidth(32); //Line thickness
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        for (int i = 0; i < FIELDY; i++) {
            for (int j = 0; j < FIELDX; j++) {
                //activateCircle(Gdx.input.getX(),Gdx.input.getY(), field[j][i]);

                if (field[j][i].getActivated()) {
                    shapeRenderer.setColor(Color.RED);
                    shapeRenderer.circle(field[j][i].getX(), field[j][i].getY(), field[j][i].getRadius());
                }else{
                    shapeRenderer.setColor(Color.BLUE);
                    shapeRenderer.circle(field[j][i].getX(), field[j][i].getY(), field[j][i].getRadius());
                }
            }
        }
        shapeRenderer.end();
	}

	//TODO wrong circle gets red, now try touchdown etc

    public void activateCircle(int x, int y, Circle circle){

        /*com.badlogic.gdx.math.Circle c = new com.badlogic.gdx.math.Circle(circle.getX(),circle.getY(),circle.getRadius());
        if (c.contains(x,y))
            circle.setActivated(true);*/
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
    public boolean longPress(float x, float y) {
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