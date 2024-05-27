package org.events;

import org.events.exceptions.EventException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Concert extends Event {

    //attributi
    private LocalTime hour;
    private BigDecimal price;

    //costruttore
    public Concert(String title, LocalDate date, int seatingCapacity, LocalTime hour, BigDecimal price) throws EventException {
        super(title, date, seatingCapacity);
        this.hour = getValidHour(hour);
        this.price = getValidPrice(price);
    }

    //metodi
    public String getFormattedPrice() {
        return String.format("%.2f€", this.getPrice());
    }

    public String getFormattedTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = hour.format(formatter);
        return String.format("%s", formattedTime);
    }


    @Override
    public String toString() {
        return String.format("%s, %s - %s - %s", this.getFormattedDate(), this.getFormattedTime(), this.getTitle(), this.getFormattedPrice());
    }

    //validare il prezzo
    private BigDecimal getValidPrice(BigDecimal price) throws EventException{
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new EventException("Il prezzo non è valido: " + price + ". Deve essere maggiore di 0" );
        }
        return price;
    }

    //validare l'ora
    private LocalTime getValidHour(LocalTime hour) throws EventException{
        if (hour == null) {
            throw new EventException("L'orario è obbligatorio" );
        }
        return hour;
    }

    //getter e setter


    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) throws EventException{
        this.hour = getValidHour(hour);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) throws EventException{
        this.price = getValidPrice(price);
    }
}
