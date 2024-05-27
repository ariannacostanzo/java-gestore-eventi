package org.events;

import org.events.exceptions.EventException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);
        LocalDate date = null;
        int seatingCapacity = 0;
        Event event = null;

        //inizio
        System.out.println("*** Benvenuto/a! Qui puoi inserire un nuovo evento e gestire il numero di prenotazioni, iniziamo! ***");
        System.out.println("-------------------------------");

        //finchè non creo l'evento continuo a chiedere titolo, data e capienza location
        while (event == null) {
            try {
                System.out.println("Inserisci il titolo dell'evento");
                String title = scanner.nextLine();
                //se il formato della data non è valido lo richiedo
                do {
                    try {
                        System.out.println("Inserisci la data (yyyy-MM-dd)");
                        String dateString = scanner.nextLine();
                        date = LocalDate.parse(dateString);
                    } catch (EventException | DateTimeParseException e) {
                        System.out.println("!!! Formato data non valido !!!");
                    }
                }
                while (date == null);
                //se il formato numero della capienza non è valido lo richiedo
                do {
                    try {
                        System.out.println("Inserisci la capacità dei posti della location");
                        seatingCapacity = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("!!! Formato numero non valido !!!");;
                    }
                }
                while (seatingCapacity == 0);
                event = new Event(title, date, seatingCapacity);
                //se qualche campo è errato mostro l'errore e ricomincio da capo
            } catch (EventException e) {
                System.out.println("!!! Qualcosa è andato storto: " + e.getMessage() + " !!!");
                System.out.println("Ricomincia daccapo");
            }
        }

        System.out.println("Hai creato questo evento: " + event);

        //menù

        boolean run = true;

        while(run) {
            System.out.println("--------------------------------------------");
            System.out.println("Il tuo evento: " + event);
            System.out.println("--------------------------------------------");
            System.out.println("Numero di posti prenotati: " + event.getBookedSeats() + "; Numero di posti disponibili: " + event.getAvailableSeats() + ";");
            System.out.println("Cosa vuoi fare?");
            System.out.println("1 - Prenotare dei posti");
            System.out.println("2 - Disdire dei posti");
            System.out.println("3 - Exit");
            String choice = scanner.nextLine();
            switch (choice) {
                //prenoto dei posti se il numero è valido e se non è maggiore della capienza
                case "1":
                    int seatsToBook = 0;
                    do {
                        try {
                            System.out.println("Quanti posti vuoi prenotare?");
                            seatsToBook = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("!!! Formato numero invalido !!!");;
                        }
                    } while(seatsToBook == 0);
                    try {
                        event.book(seatsToBook);
                        System.out.println("Prenotazione avvenuta con successo. Hai prenotato: " + seatsToBook + " posti");
                    } catch (EventException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                    //disdico dei posti se il numero è valido ed è minore della capienza e minore dei posti prenotati
                case "2":
                    int seatsToCancel = 0;
                    do {
                        try {
                            System.out.println("Quanti posti vuoi disdire?");
                            seatsToCancel = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("!!! Formato numero invalido !!!");;
                        }
                    } while (seatsToCancel == 0);
                    try {
                        event.cancel(seatsToCancel);
                        System.out.println("Cancellazione avvenuta con successo. Hai disdetto: " + seatsToCancel + " posti");
                    } catch (EventException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                    //esco se non premo 1 o 2
                default:
                    run = false;
                    break;
            }

        }

        scanner.close();
    }
}
