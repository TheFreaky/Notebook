package notebook.db;

import notebook.entity.EventDataSet;
import notebook.exceptions.DBException;
import notebook.logic.Event;
import notebook.logic.EventList;

import java.util.List;

/**
 * Created by Максим on 09.04.2017.
 */
public class DataStorageDB implements DataStorage {
        @Override
    public void load() {
        DBService db = new DBService();
        List<EventDataSet> events = null;
        try {
            events = db.getAllEvents();
        } catch (DBException e) {
            e.printStackTrace();
        }
        restoreEvents(events);
        System.out.println("Events was successfully loaded");
    }

    private void restoreEvents(List<EventDataSet> events) {
        EventList arr = EventList.getInstance();

        for (EventDataSet event : events) {
            if (event != null) {
                arr.getEvents().add(new Event(event));
            }
        }
    }

    @Override
    public void save() {

    }
}
