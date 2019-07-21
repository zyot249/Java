package com.topica.zyot.shyn;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class TestingLocalDateAndTime {
    public static void main(String[] args) {
        // local date
        LocalDate currentDate = LocalDate.now();
        System.out.println("Current date is: " + currentDate.toString());
        LocalDate myDateOfBirth = LocalDate.of(1998, Month.SEPTEMBER, 24);
        System.out.println("My date of birth is : " + myDateOfBirth);
        LocalDate date240thOfYear2018 = LocalDate.ofYearDay(2018, 240);
        System.out.println("240th day of year 2018 is : " + date240thOfYear2018);

        // local time
        LocalTime currentTime = LocalTime.now();
        System.out.println("Current time is: " + currentTime);
        LocalTime currentTimeOfParis = LocalTime.now(ZoneId.of("Europe/Paris"));
        System.out.println("Current time of Paris is: " + currentTimeOfParis);

        // formatter
        System.out.println(currentDate.format(DateTimeFormatter.ofPattern("d::MMM::uuuu")));

        // parse
        LocalDateTime dateTime = LocalDateTime.parse("27::Apr::2014 21::39::48",
                DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss"));
        System.out.println("Default format after parsing = "+dateTime);
    }
}
