package com.topica.zyot.shyn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    private static final int NUM_OF_CIRCLES = 100;
    private static final String WRONG_INPUT_MSG = "Wrong Input! Input must be INTEGER!";
    private static final String NEGATIVE_INPUT_MSG = "The area of a circle must be greater than 0!";
    private static final String REQUEST_INPUT_MSG = "\nEnter the value of area(Integer) : ";

    public static void main(String[] args) {

        List<Circle> circles = new ArrayList<>();
        for (int i = 0; i < NUM_OF_CIRCLES; i++) {
            circles.add(new Circle());
        }
        System.out.println("\n-----------FIRST LIST-----------:");
        for (Circle circle : circles) {
            System.out.println("Circle " + circles.indexOf(circle) + ": \t radius = " + circle.getRadius());
        }
        List<Circle> sortCircles = new ArrayList<>(circles);
        Collections.sort(sortCircles);
        System.out.println("\n-----------AFTER SORT-----------:");
        for (Circle circle : sortCircles) {
            System.out.println("Circle " + circles.indexOf(circle) + ": \t radius = " + circle.getRadius());
        }

        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        // initial area is negative
        int area = -1;
        while (area < 0) {
            System.out.print(REQUEST_INPUT_MSG);
            try {
                area = Integer.valueOf(reader.readLine());
                if (area < 0)
                    System.out.println(NEGATIVE_INPUT_MSG);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                System.out.println(WRONG_INPUT_MSG);
            }
        }
        System.out.println("Show input: " + area);
        getIndex(area, circles);
    }

    private static void getIndex(int area, List<Circle> circles) {
        ArrayList<Integer> indexes = new ArrayList<>(); // indexes of needed circles
        double radius = Math.sqrt((double)area / Math.PI);
        // initial delta = different btw needed radius and radius of first circle
        double delta = Math.abs((double) (radius - circles.get(0).getRadius()));
        for (Circle circle : circles) {
            double newDelta = Math.abs((double) (radius - circle.getRadius()));
            if (newDelta < delta) {
                delta = newDelta;
                indexes.clear();
                indexes.add(circles.indexOf(circle));
            } else if (newDelta == delta) {
                indexes.add(circles.indexOf(circle));
            }
        }
        System.out.println("Result:");
        for (int i : indexes) {
            System.out.println("Circle " + i
                    + " has area = " + circles.get(i).getArea()
                    + " with radius = " + circles.get(i).getRadius());
        }
    }
}
