package notebook.db;

import notebook.entity.EventDataSet;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Максим on 07.04.2017.
 */
public class Main {
    private static DBService db;
    private static Session session;
    private static EventDAO eventDAO;

    public static void main(String[] args) {
        db = new DBService();
        session = db.getSession();
        eventDAO = new EventDAO(session);

//        testInsertEvent();
//        testGetEvent();
//        testGetAll();
//        testGetEventByNameAndDate();
//        testUpdateEvent();
//        testDeleteEvent();
//        testDeleteEventById();
        testGetAll();
        System.out.println("________________________________________________");
        testGetAllSortByDate();
        System.out.println("________________________________________________");
        testGetAllSortByName();
        session.close();
        db.close();
    }

    public static boolean testInsertEvent() {
        EventDataSet event = new EventDataSet();

        event.setName("123");
        event.setDescription("312");
//        Date date = (Calendar.getInstance()).getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, 04, 24, 15, 27);
        Date date = calendar.getTime();
        event.setDate(date);
        System.out.println(date);

        session.getTransaction().begin();// instead of session.beginTransaction()
        eventDAO.insertEvent(event);
        session.getTransaction().commit();

        return true;
    }

    public static boolean testGetEvent() {
        EventDataSet eventDataSet = eventDAO.getById(19L);
        System.out.println(eventDataSet);
        return true;
    }

    public static boolean testGetAll() {
        List<EventDataSet> events = eventDAO.getAll();
        for (EventDataSet event : events) {
            System.out.println(event);
        }
        return true;
    }

    public static boolean testGetEventByNameAndDate() {
        EventDataSet event = eventDAO.getById(19L);

        EventDataSet eventDataSet = eventDAO.getByNameAndDate(event.getName(), event.getDate());
        System.out.println(eventDataSet);
        return true;
    }

    public static boolean testUpdateEvent() {
        EventDataSet event = eventDAO.getById(17L);
        System.out.println(event);

        event.setName("Seventeen");
        event.setDescription("Number seventeenth");
        session.getTransaction().begin();
        eventDAO.updateEvent(event);
        session.getTransaction().commit();

        event = eventDAO.getById(17L);
        System.out.println(event);
        return true;
    }

    public static boolean testDeleteEvent() {
        EventDataSet event = eventDAO.getById(23L);
        System.out.println(event);

        session.getTransaction().begin();
        eventDAO.deleteEvent(event);
        session.getTransaction().commit();

        event = eventDAO.getById(23L);
        System.out.println(event);
        return true;
    }

    public static boolean testDeleteEventById() {
        session.getTransaction().begin();
        eventDAO.deleteEventById(27L);
        session.getTransaction().commit();

        EventDataSet event = eventDAO.getById(27L);
        System.out.println(event);
        return true;
    }

    public static boolean testGetAllSortByDate() {
        List<EventDataSet> events = eventDAO.getAllSortByDate();
        for (EventDataSet event : events) {
            System.out.println(event);
        }
        return true;
    }

    public static boolean testGetAllSortByName() {
        List<EventDataSet> events = eventDAO.getAllSortByName();
        for (EventDataSet event : events) {
            System.out.println(event);
        }
        return true;
    }
}
