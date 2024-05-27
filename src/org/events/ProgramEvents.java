package org.events;

import org.events.exceptions.EventException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class ProgramEvents {

    //attributi
    private String title;
    private ArrayList<Event> events = new ArrayList<>();

    //costruttore
    public ProgramEvents(String title) {
        this.title = title;
    }

    //metodi
    private String getValidTitle(String title) throws EventException {
        if (title == null || title.isEmpty()) {
            throw new EventException("Il titolo è obbligatorio");

        }
        return title;
    }

    //aggiungere un evento
    public void addEvent(Event event) {
        events.add(event);
    }

    //quanti eventi sono in programma
    public int getTotalEvents() {
        return events.size();
    }

    //elimino tutti gli eventi in programma
    public void cancelEvents() {
        events.clear();
    }

    private ArrayList sortEvents(ArrayList list) {
        Collections.sort(events, Comparator.comparing(Event::getDate));
        return list;
    }


    //controllo se ho un evento in data
    public String checkEvents(LocalDate date) {
        ArrayList<Event> eventThatDay = new ArrayList<>();
        for (Event e : events) {
            if (e.getDate().equals(date)) {
                eventThatDay.add(e);
            }
        }

        if (eventThatDay.isEmpty()) {
            return "Non sono presenti eventi in data " + date;
        } else {
            sortEvents(events);
            String string =  "Gli eventi del " + date + ": ";
            for (Event e : eventThatDay) {
                String event = e.toString();
                string += event + "; ";
            }
            System.out.println(string);
            return string;
        }

    }

    @Override
    public String toString() {
        if (events.isEmpty()) {
            return "Non sono presenti eventi in programma";
        }
        sortEvents(events);
        String string =  title + " - I nostri eventi: ";
        for (Event e : events) {
            String evento = e.toString();
            string += evento + "; ";
        }
        return string;

    }



    //getter e setter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
