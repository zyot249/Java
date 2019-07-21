package com.topica.zyot.shyn.utils;

import com.topica.zyot.shyn.model.Student;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentStatistic {
    private StudentStatistic() {

    }

    // convert List to Map and use group
    public static Map<String, List<Student>> getClassStudentMap(List<Student> studentList) {
        return studentList.stream()
                .collect(Collectors.groupingBy(Student::getClassName));
    }

    // use sum
    public static double getAverageCPA(List<Student> studentList) {
        return (studentList.stream()
                .map(Student::getCpa).mapToDouble(Double::doubleValue).sum()) / (double) (studentList.size());
    }

    // use count
    public static long getScholarshipAmount(List<Student> studentList, double minCPA) {
        return studentList.stream()
                .filter(student -> student.getCpa() >= minCPA)
                .count();
    }

    // use parallel stream to count the amount of scholarships
    public static long getScholarshipAmountByParallelStream(List<Student> studentList, double minCPA) {
        return studentList.parallelStream()
                .filter(student -> student.getCpa() >= minCPA)
                .count();
    }
}
