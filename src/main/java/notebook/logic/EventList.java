package notebook.logic;

import notebook.db.DBService;
import notebook.entity.EventDataSet;
import notebook.exceptions.DBException;

import java.io.Serializable;
import java.util.*;

public class EventList implements Serializable {

    private List<Event> events = new ArrayList<>();
    private DBService db = new DBService();

    private EventList() {
    }

    private static class EventListHolder {
        public static final EventList INSTANCE_HOLDER = new EventList();
    }

    public static EventList getInstance() {
        return EventListHolder.INSTANCE_HOLDER;
    }

    public void sortByName() {
        try {
            List<EventDataSet> eventDataSets = db.getAllEventsSortedByName();
            events.clear();
            for (EventDataSet eventDataSet : eventDataSets) {
                events.add(new Event(eventDataSet));
            }
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    public void sortByDate() {
        try {
            List<EventDataSet> eventDataSets = db.getAllEventsSortedByDate();
            events.clear();
            for (EventDataSet eventDataSet : eventDataSets) {
                events.add(new Event(eventDataSet));
            }
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    public void addEvent(Event event) {
        try {
            db.insertEvent(new EventDataSet(event));
        } catch (DBException e) {
            e.printStackTrace();
        }
        events.add(event);
    }

    public void editEvent(int id, String name, String description, Calendar date) {
        Event event = new Event(events.get(id - 1));
        if (!name.isEmpty()) event.setTitle(name);
        if (!description.isEmpty()) event.setDescription(description);
        if (date != null) event.setStartDate(date);

        if (event.equals(events.get(id - 1))) return;
        try {
            db.updateEvent(new EventDataSet(event));
        } catch (DBException e) {
            e.printStackTrace();
        }
        events.set(id - 1, event);
    }

//    public void editEvent(int id, String title, String description, Calendar start, Calendar finish) {
//        editEvent(id, title, description, start);
//        Event event = events.get(id - 1);
//        if (finish != null) event.setFinishDate(finish);
//    }

    public void deleteEvent(int id) {
        EventDataSet event = new EventDataSet(events.get(id - 1));
        try {
            db.deleteEvent(event);
        } catch (DBException e) {
            e.printStackTrace();
        }
        events.remove(id - 1);
    }


    //GETTERS AND SETTERS
    //=====================================================================
    public Event getEvent(int id) {
        return new Event(events.get(id - 1));
    }

    public List<Event> getEvents() {
        return events;
    }
}