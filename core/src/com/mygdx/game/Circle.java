package com.mygdx.game;

/**
 * Created by Meerlu on 09.05.2018.
 */

public class Circle {
    private int x,y;
    private int radius;
    private Boolean activated;
    private int row,column;

    public Circle(int x, int y, int size, int row, int column) {
        this.x = x;
        this.y = y;
        this.radius = size;
        activated = false;
        this.row = row;
        this.column = column;
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

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean isNextTo(int r, int c){
        if ((r == row) || (c == column)) {
            if ((r+1 == row)|| (r-1== row)) {
                return true;
            }else if ((c+1 == column)|| (c-1== column)){
                return true;
            }else{
                return false;
            }
        }else {
            return false;
        }
    }
}
