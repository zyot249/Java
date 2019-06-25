package com.topica.zyot.shyn.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Test {
    public static void main(String[] args) {
        Class bookClass = Book.class;
        Field[] fields = bookClass.getDeclaredFields(); // getFields() --> get only public field
        for (Field field : fields) {
            MyNotNull myNotNull = field.getDeclaredAnnotation(MyNotNull.class);
            if (myNotNull != null)
                System.out.println("Field " + field.getName() + " can not be null");
            int modifier = field.getModifiers();
            if (Modifier.isFinal(modifier) && Modifier.isStatic(modifier)) {
                try {
                    System.out.println(Modifier.toString(modifier) + " "
                            + field.getType().getName() + " "
                            + field.getName() + " = "
                            + field.getFloat(null));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        Method[] methods = bookClass.getDeclaredMethods();
        for (Method method : methods) {
            MyNotNull myNotNull = method.getDeclaredAnnotation(MyNotNull.class);
            if (myNotNull != null)
                System.out.println("Method " + method.getName() + " can not return null value");
        }
    }
}
