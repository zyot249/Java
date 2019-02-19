package lecture.no3.point;

public class Point {
    private String name;
    private float x;
    private float y;

    public Point(String name, float x, float y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public void display(){
        System.out.println("Point " + name + ":");
        System.out.println("x = " + x);
        System.out.println("y = " + y);
    }
}
