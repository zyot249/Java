package com.topica.zyot.shyn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.sort;

public class Main {
    private static final int NUM_OF_CIRCLES = 100;
    private static final String MSG_WRONG_INPUT = "Wrong Input! Input must be INTEGER!";
    private static final String MSG_NEGATIVE_INPUT = "The area of a circle must be greater than 0!";
    private static final String MSG_REQUEST_INPUT = "\nEnter the value of area(Integer) : ";
    private static final String MSG_NULL_POINTER = "Null pointer!";
    private static final String MSG_EMPTY_LIST = "The list is EMPTY!";

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
        sort(sortCircles);
        System.out.println("\n-----------AFTER SORT-----------:");
        for (Circle circle : sortCircles) {
            System.out.println("Circle " + circles.indexOf(circle) + ": \t radius = " + circle.getRadius());
        }

        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        // initial area is negative
        int inputArea = -1;
        while (inputArea < 0) {
            System.out.print(MSG_REQUEST_INPUT);
            try {
                inputArea = Integer.valueOf(reader.readLine());
                if (inputArea < 0)
                    System.out.println(MSG_NEGATIVE_INPUT);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                System.out.println(MSG_WRONG_INPUT);
            }
        }
        System.out.println("Show input: " + inputArea);
        getIndexesAndArea(inputArea, circles);
    }

    private static void getIndexesAndArea(int area, List<Circle> circles) {
        if (circles == null) {
            System.out.println(MSG_NULL_POINTER);
            return;
        }
        if (circles.isEmpty()){
            System.out.println(MSG_EMPTY_LIST);
            return;
        }
        ArrayList<Integer> indexes = new ArrayList<>(); // indexes of needed circles
        double radius = Math.sqrt((double) area / Math.PI);
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
