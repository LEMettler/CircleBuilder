package com.mygdx.game;

/**
 * Created by Meerlu on 09.05.2018.
 */

public class Circle {
    private int x,y;
    private int radius;
    private Boolean activated;

    public Circle(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.radius = size;
        activated = false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }
}
