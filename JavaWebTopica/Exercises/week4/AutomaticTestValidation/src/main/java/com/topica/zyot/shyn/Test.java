package com.topica.zyot.shyn;

import java.util.HashSet;
import java.util.Set;

public class Test {
    private static final Set<String> set = new HashSet<>();
    public static void main(String[] args) {
        set.add("hello");
        System.out.println(set.contains("hello"));
    }
}
