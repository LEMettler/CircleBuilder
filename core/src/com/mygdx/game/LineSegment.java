package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.awt.Rectangle;

/**
 * Created by Meerlu on 13.05.2018.
 */

public class LineSegment {

    private float width;
    private Vector2 start;
    private Vector2 end;

    public LineSegment(float width, Vector2 start, Vector2 end) {
        this.width = width;
        this.start = start;
        this.end = end;
    }

    public float getWidth() {
        return width;
    }

    public Vector2 getStart() {
        return start;
    }

    public Vector2 getEnd() {
        return end;
    }
}
