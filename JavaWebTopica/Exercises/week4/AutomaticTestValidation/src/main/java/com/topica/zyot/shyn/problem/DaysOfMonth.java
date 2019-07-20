package com.topica.zyot.shyn.problem;

public class DaysOfMonth {
    public static void main(String[] args) {
        if (args.length != 1){
            System.out.println("wrong parameters");
        }else {
            try{
                int month = Integer.parseInt(args[0]);
                System.out.println(getDays(month));
            }catch (NumberFormatException e){
                System.out.println("wrong format");
            }
        }
    }

    public static int getDays(int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 2:
                return 28;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            default:
                return -1;
        }
    }


}
