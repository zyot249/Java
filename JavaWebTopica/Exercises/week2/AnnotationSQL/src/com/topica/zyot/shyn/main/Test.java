package com.topica.zyot.shyn.main;

import com.topica.zyot.shyn.annotation.Insert;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class Test {
    public static void main(String[] args) {
        Class queryClass = Query.class;
        Method[] methods = queryClass.getDeclaredMethods();
        for (Method method : methods) {
            Insert insertAnnotation = method.getAnnotation(Insert.class);
            if (insertAnnotation != null) {
                Parameter[] parameters = method.getParameters();
                Class typeParam = parameters[0].getType();
            }
        }
    }
}
