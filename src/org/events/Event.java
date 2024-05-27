package org.events;

import org.events.exceptions.EventException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Event {

    //attributi
    private String title;
    private LocalDate date;
    private final int seatingCapacity;
    private int bookedSeats;

    //costruttori

    public Event(String title, LocalDate date, int seatingCapacity) throws EventException{
        this.title = getValidTitle(title);
        this.date = getValidDate(date);
        this.seatingCapacity = getValidSeatingCapacity(seatingCapacity);
        this.bookedSeats = 0;

    }

    //metodi


    @Override
    public String toString() {
        return getDate() + " - " + title;
    }

    //validare la data
    private LocalDate getValidDate(LocalDate date) throws EventException{
        if (date == null || date.isBefore(LocalDate.now())) {
            throw new EventException("Invalid date");
        }
        return date;
    }

    //validare il titolo
    private String getValidTitle(String title) throws EventException{
        if (title == null || title.isEmpty()) {
            throw new EventException("Invalid title");
        }
        return title;
    }

    //validare i posti totali
    private int getValidSeatingCapacity(int seatingCapacity) throws EventException{
        if (seatingCapacity <= 0) {
            throw new EventException("Invalid seating capacity");
        }
        return seatingCapacity;
    }

    //validare i posti da prenotare
    private int getValidSeatsBooked(int bookedSeats) throws EventException{
        if (bookedSeats <= 0) {
            throw new EventException("Invalid number of seats to book");
        }
        return bookedSeats;
    }

    //prenotare

    public void book(int seatsToBook) throws EventException{
        getValidSeatsBooked(seatsToBook);
        if ( seatsToBook > seatingCapacity) {
            throw new EventException("The number of booked seats is greater than the seating capacity");
        }
        this.bookedSeats += seatsToBook;

    }

    //disdire

    public void cancel(int seatsToCancel) throws EventException {
        getValidSeatsBooked(seatsToCancel);
        if (seatsToCancel > seatingCapacity ) {
            throw new EventException("The number of seats to cancel is greater than the seating capacity");
        } else if (seatsToCancel > this.bookedSeats) {
            throw new EventException("The number of seats to cancel is greater than the number of seats booked");
        }
        this.bookedSeats -= seatsToCancel;
    }


    //getter e setter

    //titolo
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws EventException{
        this.title = getValidTitle(title);
    }

    //data
    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH);
        return date.format(formatter);
    }

    public void setDate(LocalDate date) throws EventException{
        this.date = getValidDate(date);
    }

    //posti totali
    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    //posti prenotati
    public int getBookedSeats() {
        return bookedSeats;
    }


}
