package com.kweisa.gesturescreenlock;

import java.io.Serializable;

public class Point implements Serializable {
    private double x;
    private double y;

    Point(double x, double y) {
        setX(x);
        setY(y);
    }

    void setX(double x) {
        this.x = x;
    }

    void setY(double y) {
        this.y = y;
    }

    double getX() {
        return this.x;
    }

    double getY() {
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
