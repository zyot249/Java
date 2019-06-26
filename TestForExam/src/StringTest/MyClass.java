package StringTest;

public class MyClass {
    public static void main(String[] args) {
        jill();
        jack();
        jazz();
        jizz();
    }

    public static void jill() {
        String s1 = "hill5";
        String s2 = "hill" + 5;
        System.out.println(s1==s2);
    }

    public static void jack(){
        String s1 = "hill5";
        String s2 = "hill" + s1.length();
        System.out.println(s1==s2);
    }

    public static void jazz(){
        String s1 = new String("hill5");
        String s2 = new String("hill5");
        System.out.println(s1==s2);
    }
    public static void jizz(){
        String s1 = "hill5";
        String s2 = "hill" + s1.length();
        String s3 = "hill" + s1.length();
        System.out.println(s2==s3);
    }
}
