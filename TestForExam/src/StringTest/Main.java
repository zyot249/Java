package StringTest;

public class Main {
    public static void main(String args[]) {
        Integer i1 = new Integer(6);
        Integer i2 = new Integer(7);
        swap(i1,i2);
        System.out.println(i2);
    }

    public static void swap(Integer i1, Integer i2){
        int tmp = i1;
        i1 = Integer.valueOf(i2);
        i2 = Integer.valueOf(tmp);
    }
}
