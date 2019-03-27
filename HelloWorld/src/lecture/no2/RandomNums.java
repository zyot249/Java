package lecture.no2;

import java.util.Scanner;

public class RandomNums {
    public static void main(String[] args) {
        System.out.print("How many random nums? n = ");
        Scanner inputScanner = new Scanner(System.in);
        int n = inputScanner.nextInt();
        for (int i = 0; i < n; i++) {
            System.out.println("Random num " + i + " is " + Math.random());
        }
        inputScanner.close();
    }
}
