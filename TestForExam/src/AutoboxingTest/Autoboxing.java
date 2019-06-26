package AutoboxingTest;

import java.util.ArrayList;
import java.util.List;

public class Autoboxing {
    public static void main(String[] args) {
        int x = 10;
        Integer y = x;  // boxing
        System.out.println(y);
        int a = y;      // unboxing
        System.out.println(a);

        Integer b = 10;
        Integer c = 10;
//        if (c == b) System.out.println("c = b");
        if (c != b) System.out.println("c != b");

        List<Integer> list = new ArrayList<>();
        list.add(x);
        list.add(b);
        list.add(1);
    }
}
