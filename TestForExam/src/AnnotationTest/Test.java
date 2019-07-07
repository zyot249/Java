package AnnotationTest;

public class Test {
    private static void testNotNull(String s){
        System.out.println(s);
    }
    public static void main(String[] args) {
        String s = null;
        testNotNull(s);
    }
}
