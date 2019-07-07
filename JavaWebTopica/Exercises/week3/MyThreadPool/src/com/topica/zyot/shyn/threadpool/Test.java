package com.topica.zyot.shyn.threadpool;

import java.util.Random;

public class Test {
    public static void main(String[] args) {
        CustomThreadPool customThreadPool = new CustomThreadPool(3, 8, 10);

        for (int i = 1; i <= 100; i++) {
            int duration = new Random().nextInt(100) + 1;
            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Task task = new Task("Task " + i);
            System.out.println("Created : " + task.getName());
            customThreadPool.execute(task);
            System.out.println("Added : " + task.getName());
        }
        try {
            Thread.sleep(6000);
            for (int i = 101; i <= 110; i++) {
                Task task = new Task("Task " + i);
                System.out.println("Created : " + task.getName());
                customThreadPool.execute(task);
                System.out.println("Added : " + task.getName());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        customThreadPool.shutdown();
    }
}
