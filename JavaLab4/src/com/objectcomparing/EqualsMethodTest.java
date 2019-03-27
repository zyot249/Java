package com.objectcomparing;

class MyValue {
    int i;

    public boolean equals(Object obj) {
        return (this.i == ((MyValue) obj).i);
    }
}
public class EqualsMethodTest {
    public static void main(String[] args) {
        MyValue v1 = new MyValue();
        MyValue v2 = new MyValue();
        v1.i = v2.i = 100;
        System.out.println("Result 5: " + (v1 == v2));
        System.out.println("Result 6: " + v1.equals(v2));
    }
}
