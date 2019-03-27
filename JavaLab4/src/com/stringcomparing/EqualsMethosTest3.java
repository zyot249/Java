package com.stringcomparing;

public class EqualsMethosTest3 {
    public static void main(String[] args) {
        String s1 = "Hello";
        String s2 = "Hello";
        System.out.println(s1 == s2);
        String s3 = new String("Goodbye");
        String s4 = new String("Goodbye");
        System.out.println("Result 7: " + (s3==s4));
        String name = "JoeSmith";
        System.out.println("Result 8: " + "JoeSmith".equals(name));
        System.out.println("Result 9: " + "JoeSmith".equalsIgnoreCase("joEsMIth"));
    }
}
