package com.topica.zyot.shyn.problem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// testing small part
public class CodeExecution {
//    public static void main(String[] args) {
//        Map<String, Integer> scores = new HashMap<>();
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        Map<String, Set<String>> fileJavaPaths = new HashMap<>();
//        Set<String> paths1 = new HashSet<>();
//        paths1.add("output/20160674@student.hust.edu.vn-DaysOfMonthWrong/DaysOfMonthWrong.java");
//        fileJavaPaths.put("20160674@student.hust.edu.vn", paths1);
//        Set<String> paths2 = new HashSet<>();
//        paths2.add("output/zyot249@gmail.com-DaysOfMonth/DaysOfMonth.java");
//        fileJavaPaths.put("zyot249@gmail.com", paths2);
//
//        fileJavaPaths.forEach((address, paths) -> paths.forEach(path -> {
//            CodeExecutionWorker worker = new CodeExecutionWorker(path);
//            int grade = 0;
//            try {
//                grade = executorService.submit(worker).get();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//            Integer currentGrade = scores.get(address);
//            if (currentGrade != null) {
//                if (grade > currentGrade) {
//                    scores.replace(address, grade);
//                }
//            } else
//                scores.put(address, grade);
//        }));
//
//        if (!scores.isEmpty()) {
//            scores.forEach((address, score) -> System.out.println(address + " : " + score));
//        }
//        executorService.shutdown();
//    }
}
