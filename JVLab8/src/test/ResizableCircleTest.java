package test;

import shape.ResizableCircle;

public class ResizableCircleTest {
    public static void main(String[] args) {
        ResizableCircle rc1 = new ResizableCircle(6.6);
        System.out.println(rc1);
        rc1.resize(80);
        System.out.println(rc1);
    }
}
