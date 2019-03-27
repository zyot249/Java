package lecture.no2;

import java.util.Scanner;

public class CalculatePay {
    public static void main(String[] args) {
        final double RATE = 8.25;
        final int STANDARD = 40;
        System.out.print("Enter the number of hours worked : ");
        Scanner sc = new Scanner(System.in);
        int hours = sc.nextInt();

        double pay = 0.0;
        if(hours > STANDARD)
            pay = STANDARD * RATE + (hours - STANDARD) * (RATE * 1.5);
        else
            pay = hours * RATE;

        System.out.println("\nShynnnnnn earnings: " + pay);
        sc.close();
    }
}
