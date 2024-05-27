package org.events;

import org.events.exceptions.EventException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Formatter;

public class Test {
    public static void main(String[] args) {


        //date
        String dateString = "2024-07-25";
        LocalDate date = LocalDate.parse(dateString);
        String dateString2 = "2024-08-15";
        LocalDate date2 = LocalDate.parse(dateString2);
        String dateString3 = "2024-06-15";
        LocalDate date3 = LocalDate.parse(dateString3);
        String dateString4 = "2024-07-05";
        LocalDate date4 = LocalDate.parse(dateString4);
        //orario
        String hourString = "14:00";
        LocalTime hour = LocalTime.parse(hourString);
        String hourString2 = "18:30";
        LocalTime hour2 = LocalTime.parse(hourString2);
        String hourString3 = "19:40";
        LocalTime hour3 = LocalTime.parse(hourString3);
        //prezzo
        BigDecimal price = new BigDecimal("75.3");
        BigDecimal price2 = new BigDecimal("65.99");
        BigDecimal price3 = new BigDecimal("49.50");
//

        //istanzio altri 2 eventi(arbitrariamente giusti)
        Concert concert = new Concert("Linkin Park", date, 500, hour, price);
        Concert concert2 = new Concert("Three Days Grace", date2, 1000, hour2, price2);
        Concert concert3 = new Concert("Ratatat", date3, 1500, hour3, price3);
        Event event = new Event("Etnacomics", date4, 700);
        Event event2 = new Event("Lucca Comics", date4, 1500);

        System.out.println(concert);
        System.out.println(concert2);
        System.out.println(concert3);
        System.out.println(event);

        ProgramEvents program = new ProgramEvents("Estate 2024");
        program.addEvent(concert);
        program.addEvent(concert2);
        program.addEvent(concert3);
        program.addEvent(event);
        program.addEvent(event2);


        program.checkEvents(date4);

        System.out.println(program);
        System.out.println("Sono presenti " + program.getTotalEvents() + " eventi in programma");
        program.cancelEvents();
        System.out.println(program);


    }
}
