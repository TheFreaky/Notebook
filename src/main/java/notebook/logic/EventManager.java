package notebook.logic;

import notebook.exceptions.IllegalDateFormatException;
import notebook.exceptions.IllegalDatesSequenceException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class EventManager {
    private static EventList eventList = EventList.getInstance();

    public static void addEvent(String title, String description, String date)
            throws IllegalDatesSequenceException, IllegalDateFormatException {

        eventList.addEvent(new Event(title, description, scanOrdinaryDate(date)));
    }

    public static void editEvent(int id, String title, String description, String date)
            throws IllegalDatesSequenceException, IllegalDateFormatException {

        eventList.editEvent(id, title, description, scanOrdinaryDate(date));

    }

    public static ArrayList<String> showEvents() {
        ArrayList<String> stringEvents = new ArrayList<>();

        for (Event event : eventList.getEvents()) {
            stringEvents.add(event.toString());
        }

        return stringEvents;
    }

    // scan date in format dd/mm/yyyy
    private static Calendar scanOrdinaryDate(String date) throws IllegalDateFormatException {
        return scanDate(date, Event.DAY_EVENT);
    }

    private static Calendar scanDate(String date, String dateFormat) throws IllegalDateFormatException {
        Calendar calendar = Calendar.getInstance();
        calendar.setLenient(false);
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        format.setLenient(false);

        try {
            calendar.setTime(format.parse(date));
        } catch (ParseException e) {
            throw new IllegalDateFormatException();
        }
        return calendar;
    }
}