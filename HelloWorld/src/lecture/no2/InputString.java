package lecture.no2;

import java.util.Scanner;

public class InputString {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhap mot dong:");
        String s = sc.nextLine();
        System.out.println("Dong vua nhap: " +s);
        sc.close();
    }
}
