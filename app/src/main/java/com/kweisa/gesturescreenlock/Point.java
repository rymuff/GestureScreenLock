package com.kweisa.gesturescreenlock;

import java.io.Serializable;

public class Point implements Serializable {
    private float x;
    private float y;

    Point(float x, float y) {
        setX(x);
        setY(y);
    }

    Point(int x, int y) {
        setX(x);
        setY(y);
    }

    void setX(float x) {
        this.x = x;
    }

    void setY(float y) {
        this.y = y;
    }

    float getX() {
        return this.x;
    }

    float getY() {
        return this.y;
    }

    @Override
    public int hashCode() {
        return 31 * (int) x + (int) y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
