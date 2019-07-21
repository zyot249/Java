package com.topica.zyot.shyn;

import com.topica.zyot.shyn.model.Student;
import com.topica.zyot.shyn.utils.StudentStatistic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student("20160674", "Nguyen Duc Dung", "ICT.02-K61", "Nam Dinh", 3.23));
        students.add(new Student("20160343", "Nguyen Duc Anh", "ICT.01-K61", "Ha Noi", 3.67));
        students.add(new Student("20166563", "Tran Duc Dung", "ICT.02-K61", "Nam Dinh", 3.10));
        students.add(new Student("20164343", "Vu Thanh Tung", "ICT.02-K61", "Nam Dinh", 3.30));
        students.add(new Student("20160345", "Chu The Chuong", "ICT.02-K61", "Bac Giang", 3.78));
        students.add(new Student("20166789", "Tran Bich Ngoc", "ICT.02-K61", "Bac Giang", 3.89));
        students.add(new Student("20165678", "Phan Ngoc Toan", "ICT.01-K61", "Ha Noi", 3.51));
        students.add(new Student("20164567", "Nguyen Minh Hieu", "ICT.01-K61", "Ha Noi", 3.45));
        students.add(new Student("20164765", "Dao Ngoc Thanh", "ICT.02-K61", "Hung Yen", 3.03));
        students.add(new Student("20161234", "Tran Minh Duc", "ICT.02-K61", "Ha Noi", 3.00));

        // group student by class
        Map<String, List<Student>> classStudentMap = StudentStatistic.getClassStudentMap(students);
        classStudentMap.forEach((className, studentList) -> studentList.forEach(student ->
                System.out.println(new StringBuilder()
                        .append(className)
                        .append(" : ")
                        .append(student.getFullname())
                        .toString())
        ));

        // get average cpa
        System.out.println("Average CPA : " + StudentStatistic.getAverageCPA(students));

        // get amount of scholarships
        double minCPA = 3.5;
        System.out.println("Number of scholarships is : " + StudentStatistic.getScholarshipAmount(students, minCPA));

    }
}
