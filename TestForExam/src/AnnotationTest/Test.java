package AnnotationTest;

import com.sun.istack.internal.Nullable;

public class Test {
    private static void testNotNull(@Nullable String s){
        System.out.println(s);
    }
    public static void main(String[] args) {
        String s = null;
        testNotNull(s);
    }
}
