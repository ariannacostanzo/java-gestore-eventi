package org.events;

import org.events.exceptions.EventException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Formatter;

public class Test {
    public static void main(String[] args) {

        LocalDate date = null;
        try {
            String dateString = "2025-07-25";
            date = LocalDate.parse(dateString);
        } catch (Exception e) {
            System.out.println("Formato data non valido");
        }
        LocalTime hour = null;
        try {
            String hourString = "14:00";
            hour = LocalTime.parse(hourString);
        } catch (Exception e) {
            System.out.println("Formato orario non valido");
        }


        BigDecimal price = new BigDecimal("10.50");

        Concert concert = null;
        try {
            concert = new Concert("Linkin Park", date, 1000, hour, price);
        } catch (EventException e) {
            System.out.println(e.getMessage());
        }

        if (concert != null) {
            System.out.println(concert);

        }

    }
}
