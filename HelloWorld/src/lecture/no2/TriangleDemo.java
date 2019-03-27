package lecture.no2;

import java.util.Scanner;

public class TriangleDemo {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the height of triangle: h = ");
        int h = sc.nextInt();
        System.out.println("Result: ");
        printSquareTriangle(h);
        System.out.println("-----------------------------------------------------------------");
        printBalanceTriangle(h);
    }

    private static void printSquareTriangle(int h) {
        for (int i = 0; i < h; i++) {
            for (int j = 0; j <= i; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }

    private static void printBalanceTriangle(int h) {
        for (int i = 1; i <= h; i++) {
            for (int j = 1;j<= h-i;j++)
                System.out.print(" ");
            for (int k = 1; k<= 2*i - 1;k++)
                System.out.print("*");
            System.out.println();
        }

    }
}
