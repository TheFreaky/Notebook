package notebook.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class EventList implements Serializable {

    private ArrayList<Event> events = new ArrayList<>();

    private EventList() {
    }

    private static class EventListHolder {
        public static final EventList INSTANCE_HOLDER = new EventList();
    }

    public static EventList getInstance() {
        return EventListHolder.INSTANCE_HOLDER;
    }

    public void sortByTitle() {
        Comparator<Event> compByTitle = new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                return o1.getTitle().compareToIgnoreCase(o2.getTitle());
            }
        };
        Collections.sort(events, compByTitle);
    }

    public void sortByDate() {
//        Comparator<Event> compByDate = new Comparator<Event>() {
//            @Override
//            public int compare(Event o1, Event o2) {
//                return o1.getStartDate().compareTo(o2.getStartDate());
//            }
//        };
        Comparator<Event> compByDate = Comparator.comparing(Event::getStartDate);
        Collections.sort(events, compByDate);
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void editEvent(int id, String title, String description, Calendar start) {
        Event event = events.get(id - 1);
        if (!title.isEmpty()) event.setTitle(title);
        if (!description.isEmpty()) event.setDescription(description);
        if (start != null) event.setStartDate(start);
    }

    public void editEvent(int id, String title, String description, Calendar start, Calendar finish) {
        editEvent(id, title, description, start);
        Event event = events.get(id - 1);
        if (finish != null) event.setFinishDate(finish);
    }

    public void deleteEvent(int id) {
        events.remove(id - 1);
    }


    //GETTERS AND SETTERS
    //=====================================================================
    public Event getEvent(int id) {
        return new Event(events.get(id - 1));
    }

    public ArrayList<Event> getEvents() {
        return new ArrayList<>(events);
    }
}