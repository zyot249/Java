package Creational.Factory;

public class Test {
    public static void main(String[] args) {
        Shape c = ShapeFactory.getShape("Circle");
        c.draw();
    }
}
