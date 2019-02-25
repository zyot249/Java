package lecture.no3.utils;

import java.util.Calendar;
import java.util.Scanner;

public class MyDate {
    private int date;
    private int month;
    private int year;

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void enterDate() {
        int mDate;
        int mMonth;
        int mYear;

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the date (dd/mm/yyyy): ");
        String tmp = sc.nextLine();
        mDate = Integer.parseInt(tmp.substring(0, 2));
        mMonth = Integer.parseInt(tmp.substring(3, 5));
        mYear = Integer.parseInt(tmp.substring(6, 10));

        switch (mMonth) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                if (mDate >= 1 && mDate <= 31) {
                    setDate(mDate);
                    setMonth(mMonth);
                    setYear(mYear);
                } else System.out.println("Wrong Date");
                break;
            case 2:
                if ((mYear % 4 == 0 && mYear % 100 != 0) || (mYear % 400 == 0))
                    if (mDate >= 1 && mDate <= 29) {
                        setDate(mDate);
                        setMonth(mMonth);
                        setYear(mYear);
                    } else System.out.println("Wrong Date");
                else if (mDate >= 1 && mDate <= 28) {
                    setDate(mDate);
                    setMonth(mMonth);
                    setYear(mYear);
                } else System.out.println("Wrong Date");
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                if (mDate >= 1 && mDate <= 30) {
                    setDate(mDate);
                    setMonth(mMonth);
                    setYear(mYear);
                } else System.out.println("Wrong Date");
                break;
            default:
                System.out.println("The month must be from 1-12");

        }

    }

    public void display() {
        if (getDate() != 0)
            if (getDate() >= 10)
                if (getMonth() >= 10)
                    System.out.println("Date : " + getDate() + "/" + getMonth() + "/" + getYear());
                else System.out.println("Date : " + getDate() + "/0" + getMonth() + "/" + getYear());
            else System.out.println("Date : 0" + getDate() + "/0" + getMonth() + "/" + getYear());
    }
}
