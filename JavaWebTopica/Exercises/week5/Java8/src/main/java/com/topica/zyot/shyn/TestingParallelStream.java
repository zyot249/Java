package com.topica.zyot.shyn;

import com.topica.zyot.shyn.model.Student;
import com.topica.zyot.shyn.utils.StudentStatistic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestingParallelStream {
    private static final int NUM_OF_STUDENTS = 100000;
    private static final double MIN_CPA = 3.2;
    private static final int STANDARD_GRADE = 4;

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>(NUM_OF_STUDENTS);
        Random random = new Random();
        for (int i = 0; i < NUM_OF_STUDENTS; i++) {
            students.add(new Student(random.nextDouble() * STANDARD_GRADE));
        }

        long start;
        long end;
        start = System.currentTimeMillis();
        System.out.println("Number of scholarships is : " + StudentStatistic.getScholarshipAmount(students, MIN_CPA));
        end = System.currentTimeMillis();
        System.out.println(new StringBuilder().append("Duration = ").append(end - start).append(" ms").toString());

        start = System.currentTimeMillis();
        System.out.println("Number of scholarships is(parallel stream) : " + StudentStatistic.getScholarshipAmountByParallelStream(students, MIN_CPA));
        end = System.currentTimeMillis();
        System.out.println(new StringBuilder().append("Duration = ").append(end - start).append(" ms").toString());
    }
}
