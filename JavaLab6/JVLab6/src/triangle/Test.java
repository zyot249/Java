package triangle;

public class Test {
    public static void main(String[] args) {
        MyPoint v1 = new MyPoint(0,2);
        MyPoint v2 = new MyPoint(3,4);
        MyPoint v3 = new MyPoint(-3,4);

        MyTriangle myTriangle = new MyTriangle(v1, v2, v3);

        System.out.println(myTriangle.getType());
    }
}
