package org.events;

import org.events.exceptions.EventException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainBonus {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Event event = null;
        //inizio
        System.out.println("*** Benvenuto/a! Qui puoi creare un nuovo programma e gestirne gli eventi, iniziamo! ***");
        System.out.println("-------------------------------");

        ProgramEvents program = null;
        while (program == null) {
            System.out.println("Inserisci il titolo del programma");
            String title = scanner.nextLine();
            try {
                program = new ProgramEvents(title);
            } catch (EventException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Hai creato con successo: " + program.getTitle());
        boolean play = true;

        while (play) {
            System.out.println("------------------------------");
            System.out.println("Cosa vuoi fare?");
            System.out.println("1 - Aggiungi un evento");
            System.out.println("2 - Gestisci un evento");
            System.out.println("3 - Svuota il programma");
            System.out.println("4 - Consulta il programma");
            System.out.println("5 - Scopri gli eventi in una data");
            System.out.println("0 - Exit");
            System.out.println("------------------------------");
            String option = scanner.nextLine();
            switch (option) {
                case "0":
                    play = false;
                    break;
                case "1":
                    Event newEvent = createEvent(scanner, event);
                    program.addEvent(newEvent);
                    break;
                case "2":
                    if (program.getTotalEvents() == 0) {
                        System.out.println(program);
                    } else {
                        System.out.println("Scelgi l'evento da gestire: ");
                        ArrayList<Event> programEvents = program.getEvents();
                        for (int i = 0; i < programEvents.size(); i++) {
                            System.out.println(programEvents.get(i).getTitle());
                        }
                        String chosenTitle = scanner.nextLine();
                        for (Event e : programEvents) {
                            if (chosenTitle.equalsIgnoreCase(e.getTitle())) {
                                handleEvent(scanner, e);
                            } else {
                                System.out.println("L'evento non è stato trovato");
                            }
                        }
                    }

                    break;
                case "3":
                    program.cancelEvents();
                    System.out.println("Hai svuotato con successo il programma " + program.getTitle());
                    break;
                case "4":
                    System.out.println("Cosa scegli di fare?");
                    System.out.println("1 - Scopri gli eventi in ordine di data");
                    System.out.println("2 - Scopri il numero di eventi in programma");
                    String programChoice = scanner.nextLine();
                    switch (programChoice) {
                        case "1":
                            System.out.println(program);
                            break;
                        case "2":
                            if (program.getTotalEvents() == 1) {
                                System.out.println("E' presente 1 evento in programma");
                            } else {
                                System.out.println("Sono presenti " + program.getTotalEvents() + " eventi in programma");
                            }
                    }
                    break;
                case "5":
                    System.out.println("Inserisci la data dell'evento (yyyy-MM-dd)");
                    try {
                        String dateString = scanner.nextLine();
                        LocalDate date = LocalDate.parse(dateString);
                        String result = program.checkEvents(date);
                        System.out.println(result);
                    } catch (Exception e) {
                        System.out.println("Formato data non valido");
                    }
                    break;
                default:
                    System.out.println("Input non valido");
                    break;
            }
        }


        scanner.close();

    }

    private static void handleEvent(Scanner scanner, Event event) {
        boolean run = true;
        System.out.println("--------------------------------------------");
        System.out.println("Il tuo evento: " + event);
        System.out.println("--------------------------------------------");
        System.out.println("Numero di posti prenotati: " + event.getBookedSeats() + "; Numero di posti disponibili: " + event.getAvailableSeats() + ";");
        System.out.println("Cosa vuoi fare?");
        System.out.println("1 - Prenotare dei posti");
        System.out.println("2 - Disdire dei posti");
        System.out.println("3 - Torna al programma");
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
    private static LocalTime getTimeFromUser(Scanner scanner) {
        LocalTime time = null;
        while (time == null) {
            try {
                System.out.println("Inserisci l'orario (hh:mm)");
                String timeString = scanner.nextLine();
                time = LocalTime.parse(timeString);
            } catch (DateTimeParseException e) {
                System.out.println("!!! Formato orario non valido !!!");
            }
        }
        return time;
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

    private static BigDecimal getPriceFromUser(Scanner scanner) {
        BigDecimal price = null;
        while (price == null) {
            try {
                System.out.println("Inserisci il prezzo");
                double priceDouble = Double.parseDouble(scanner.nextLine());
                price = new BigDecimal(priceDouble);
            } catch (NumberFormatException e) {
                System.out.println("Formato numero non valido");;
            }
        }
        return price;
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


    private static Event createEvent(Scanner scanner, Event event) {

        System.out.println("Che tipo di evento vuoi aggiungere?");
        System.out.println("1 - Evento");
        System.out.println("2 - Concerto");
        String eventChoice = scanner.nextLine();

        //finchè non creo l'evento continuo a chiedere titolo, data e capienza location
        while (event == null) {
            try {
                //ottengo titolo, data e capacità
                System.out.println("Inserisci il titolo dell'evento");
                String title = scanner.nextLine();

                LocalDate date = getDateFromUser(scanner);
                int seatingCapacity = getSeatingCapacityFromUser(scanner);

                if (eventChoice.equals("2")) {


                    LocalTime time = getTimeFromUser(scanner);

                    BigDecimal price = getPriceFromUser(scanner);


                    event = new Concert(title, date, seatingCapacity, time, price);



                } else {
                    //inizializzo l'evento
                    event = new Event(title, date, seatingCapacity);
                }

                //se qualche campo è errato mostro l'errore e ricomincio da capo
            } catch (EventException e) {
                System.out.println("!!! Qualcosa è andato storto: " + e.getMessage() + " !!!");
                System.out.println("Ricomincia daccapo");
            }
        }

        System.out.println("Hai creato questo evento: " + event);
        return event;
    }

}
