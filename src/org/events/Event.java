package org.events;

import org.events.exceptions.EventException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Event{

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
        return getFormattedDate() + " - " + title;
    }

    //validare la data
    private LocalDate getValidDate(LocalDate date) throws EventException{
        if (date == null || date.isBefore(LocalDate.now())) {
            throw new EventException("La data non è valida: " + date + ". La data non può essere già passata");
        }
        return date;
    }

    //validare il titolo
    private String getValidTitle(String title) throws EventException{
        if (title == null || title.isEmpty()) {
            throw new EventException("Il titolo è obbligatorio");

        }
        return title;
    }

    //validare i posti totali
    private int getValidSeatingCapacity(int seatingCapacity) throws EventException{
        if (seatingCapacity <= 0) {
            throw new EventException("La capienza della location non è valida: " + seatingCapacity + ". Deve essere un numero positivo");
        }
        return seatingCapacity;
    }

    //validare i posti da prenotare
    private int getValidSeatsBooked(int bookedSeats) throws EventException{
        if (bookedSeats <= 0) {
            throw new EventException("Numero di posti da prenotare non valido: " + bookedSeats + ". Deve essere un numero positivo, minore della capienza");
        }
        return bookedSeats;
    }

    //prenotare

    public void book(int seatsToBook) throws EventException{
        getValidSeatsBooked(seatsToBook);
        if ( seatsToBook > seatingCapacity) {
            throw new EventException("Il numero di posti prenotati è maggiore della capienza della location");
        }
        this.bookedSeats += seatsToBook;

    }

    //disdire

    public void cancel(int seatsToCancel) throws EventException {
        getValidSeatsBooked(seatsToCancel);
        if (seatsToCancel > seatingCapacity ) {
            throw new EventException("Il numero dei posti da cancellare è maggiore della capienza della location");
        } else if (seatsToCancel > this.bookedSeats) {
            throw new EventException("Il numero dei posti da cancellare è maggiore della quantità dei posti prenotati");
        }
        this.bookedSeats -= seatsToCancel;
    }

    public int getAvailableSeats() {
        return this.seatingCapacity - this.bookedSeats;
    }

    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ITALIAN);
        return date.format(formatter);
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
    public LocalDate getDate() {
        return date;
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
