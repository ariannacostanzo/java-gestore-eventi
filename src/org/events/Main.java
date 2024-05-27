package org.events;

import org.events.exceptions.EventException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Event event = null;

        //inizio
        System.out.println("*** Benvenuto/a! Qui puoi inserire un nuovo evento e gestire il numero di prenotazioni, iniziamo! ***");
        System.out.println("-------------------------------");

        //finchè non creo l'evento continuo a chiedere titolo, data e capienza location
        while (event == null) {
            try {
                //ottengo titolo, data e capacità
                System.out.println("Inserisci il titolo dell'evento");
                String title = scanner.nextLine();

                LocalDate date = getDateFromUser(scanner);
                int seatingCapacity = getSeatingCapacityFromUser(scanner);

                //inizializzo l'evento
                event = new Event(title, date, seatingCapacity);
                //se qualche campo è errato mostro l'errore e ricomincio da capo
            } catch (EventException e) {
                System.out.println("!!! Qualcosa è andato storto: " + e.getMessage() + " !!!");
                System.out.println("Ricomincia daccapo");
            }
        }

        System.out.println("Hai creato questo evento: " + event);
        boolean run = true;

        //menù
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
                case "1":
                    book(scanner, event);
                    break;
                case "2":
                    cancel(scanner, event);
                    break;
                //esco se non premo 1 o 2
                case "3":
                    run = false;
                    break;
                default:
                    System.out.println("Input non valido");
                    break;
            }
        }
        scanner.close();
    }

    //se il formato della data non è valido lo richiedo
    private static LocalDate getDateFromUser(Scanner scanner) {
        LocalDate date = null;
        while (date == null) {
            try {
                System.out.println("Inserisci la data (yyyy-MM-dd)");
                String dateString = scanner.nextLine();
                date = LocalDate.parse(dateString);
            } catch (DateTimeParseException e) {
                System.out.println("!!! Formato data non valido !!!");
            }
        }
        return date;
    }

    //se il formato numero della capienza non è valido lo richiedo
    private static int getSeatingCapacityFromUser(Scanner scanner) {
        int seatingCapacity = 0;
        while (seatingCapacity == 0) {
            try {
                System.out.println("Inserisci la capacità dei posti della location");
                seatingCapacity = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("!!! Formato numero non valido !!!");
            }
        }
        return seatingCapacity;
    }

    //prenoto dei posti se il numero è valido e se non è maggiore della capienza
    private static void book(Scanner scanner, Event event) {
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
    }

    //disdico dei posti se il numero è valido ed è minore della capienza e minore dei posti prenotati
    private static void cancel(Scanner scanner, Event event) {
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
    }
}
