package com.topica.zyot.shyn;

import java.util.concurrent.ForkJoinPool;

public class App {
    public static void main(String[] args) {

        ForkJoinFibonacci task = new ForkJoinFibonacci(50);
        new ForkJoinPool().invoke(task);

        System.out.println(task.getNumber());

    }
}
