package case1;

public class Two extends One {
//    @Override
    void f() {
        count = count * 3;
        System.out.println("In Two, count = " + count);
    }

    void f1(){
        System.out.println("In Two, ccl");
    }

    public static void test(){
        System.out.println("hihi");
    }
    public void test2(){
        System.out.println("haha");
    }
}
