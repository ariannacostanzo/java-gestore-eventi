package org.events;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        String dateString = "2024-06-12";
        LocalDate date = LocalDate.parse(dateString);
        Event evento = new Event("asa", date, 1000);

        System.out.println(evento.toString());

        System.out.println(evento.getBookedSeats());
        evento.book(100);
        System.out.println(evento.getBookedSeats());
        evento.cancel(0);
        System.out.println(evento.getBookedSeats());

    }
}
