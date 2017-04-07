package notebook.db;

import notebook.logic.Event;
import notebook.logic.EventList;

import java.io.*;
import java.util.ArrayList;

public class DataSerializator implements DataStorage {

    public void save() {
        String fileName = System.getProperty("user.home") + System.getProperty("file.separator") + "events.ser";
        try (FileOutputStream fs = new FileOutputStream(fileName);
             ObjectOutputStream os = new ObjectOutputStream(fs)) {

            os.writeObject(EventList.getInstance().getEvents());
            System.out.println("Events was successfully saved");
        } catch (IOException e) {
            System.err.println("Couldn't save data");
        }
    }

    public void load() {
        String fileName = System.getProperty("user.home") + System.getProperty("file.separator") + "events.ser";
        try (FileInputStream fs = new FileInputStream(fileName);
             ObjectInputStream os = new ObjectInputStream(fs)) {

            Object one = os.readObject();
            ArrayList<Event> events = (ArrayList<Event>) one;
            restoreEvents(events);
            System.out.println("Events was successfully loaded");
        } catch (Exception e) {
            System.err.println("Couldn't load data");
        }
    }

    private void restoreEvents(ArrayList<Event> events) {
        EventList arr = EventList.getInstance();

        for (Event event : events) {
            arr.addEvent(event);
        }
    }
}
