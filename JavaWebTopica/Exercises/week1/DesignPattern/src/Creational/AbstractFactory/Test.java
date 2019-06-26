package Creational.AbstractFactory;

public class Test {
    public static void main(String[] args) {
        AbstractFactory shapeFactory = FactoryProducer.getFactory(true);
        Shape shape1 = shapeFactory.getShape("Rectangle");
        shape1.draw();
    }
}
