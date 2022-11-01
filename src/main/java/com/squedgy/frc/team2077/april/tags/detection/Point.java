package com.squedgy.frc.team2077.april.tags.detection;

public class Point {
    private final double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getY() {return y;}
    public double getX() {return x;}

    @Override public String toString() {
        return String.format("(%.0f, %.0f)", x, y);
    }
}
