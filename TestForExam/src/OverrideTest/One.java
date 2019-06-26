package OverrideTest;

public class One {
    public int count = 1;
    void f2(){
        System.out.println("In One, count = " + count++);
    }
    public static void test(){
        System.out.println("hello");
    }

    protected void test2(){
        System.out.println("hi");
    }

    public void callTest(){
        test();
        test2();
    }

}
