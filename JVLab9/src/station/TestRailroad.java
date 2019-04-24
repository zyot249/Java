package station;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;


public class TestRailroad {
    public static void main(String[] args) {
        Line rail = new Line();
        System.out.println("OneWayTicket");
        OneWayTicket owTicket1 = new OneWayTicket(new Date(), 400);
        rail.gates[0].enter(owTicket1);
        rail.gates[1].exit(owTicket1);

        System.out.println("\nCard");
        PrepaidCard card1 = new PrepaidCard(new Date(), 0);
        card1.add(100);
        rail.gates[1].enter(card1);
        rail.gates[3].exit(card1);

        System.out.println("\nOneDayTicket");
        OneDayTicket odTicket1 = new OneDayTicket(Calendar.getInstance().getTime());
        int opt = 0;
        int start, end;
        end = 0;
        do {
            System.out.println("1. Enter");
            System.out.println("2. Exit");
            System.out.println("Your choice: ");
            Scanner sc = new Scanner(System.in);
            opt = sc.nextInt();

            switch (opt) {
                case 1: {
                    System.out.println("Enter: ");
                    printStation();
                    start = sc.nextInt();
                    rail.gates[start - 1].enter(odTicket1);
                    if (odTicket1.isValid()) {
                        System.out.println("Exit: ");
                        while (end == 0) {
                            printStation();
                            end = sc.nextInt();
                            rail.gates[end - 1].exit(odTicket1);
                        }
                        end = 0;
                    }
                    break;
                }
            }

        } while (opt != 2);
    }

    static void printStation() {
        System.out.println("Welcome to Railroad");
        System.out.println("1. Station A");
        System.out.println("2. Station B");
        System.out.println("3. Station C");
        System.out.println("4. Station D");
        System.out.println("Your choice: ");
    }
}
