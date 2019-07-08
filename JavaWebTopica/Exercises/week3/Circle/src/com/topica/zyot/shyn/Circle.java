package com.topica.zyot.shyn;

import java.util.Random;

public class Circle implements Comparable {
    private final int RANGE_OF_RADIUS = 100;
    private int radius;

    public int getRadius() {
        return radius;
    }

    public double getArea() {
        return Math.PI * radius * radius;
    }

    public Circle() {
        this.radius = new Random().nextInt(RANGE_OF_RADIUS) + 1;
    }

    @Override
    public int compareTo(Object o) {
        return radius - ((Circle) o).getRadius();
    }
}
