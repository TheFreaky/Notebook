package notebook.db;

import notebook.entity.EventDataSet;
import notebook.exceptions.DBException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class DBService {
    private static final SessionFactory sessionFactory;

    static {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }

    public void close() {
        sessionFactory.close();
    }


    public EventDataSet getEvent(long id) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            EventDAO dao = new EventDAO(session);
            EventDataSet dataSet = dao.getById(id);
            session.close();
            return dataSet;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public List<EventDataSet> getAllEvents() throws DBException {
        try {
            Session session = sessionFactory.openSession();
            EventDAO dao = new EventDAO(session);
            List<EventDataSet> events = dao.getAll();
            session.close();
            return events;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public List<EventDataSet> getAllEventsSortedByDate() throws DBException {
        try {
            Session session = sessionFactory.openSession();
            EventDAO dao = new EventDAO(session);
            List<EventDataSet> events = dao.getAllSortByDate();
            session.close();
            return events;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public List<EventDataSet> getAllEventsSortedByName() throws DBException {
        try {
            Session session = sessionFactory.openSession();
            EventDAO dao = new EventDAO(session);
            List<EventDataSet> events = dao.getAllSortByName();
            session.close();
            return events;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public void insertEvent(EventDataSet event) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            session.getTransaction().begin();
            EventDAO dao = new EventDAO(session);
            dao.insertEvent(event);
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public void updateEvent(EventDataSet event) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            session.getTransaction().begin();
            EventDAO dao = new EventDAO(session);
            dao.updateEvent(event);
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public void deleteEvent(EventDataSet event) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            session.getTransaction().begin();
            EventDAO dao = new EventDAO(session);
            dao.deleteEvent(event);
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

//
//    public UsersDataSet getUser(String login) throws DBException {
//        try {
//            Session session = sessionFactory.openSession();
//            UsersDAO dao = new UsersDAO(session);
//            UsersDataSet dataSet = dao.getById(login);
//            session.close();
//            return dataSet;
//        } catch (HibernateException e) {
//            throw new DBException(e);
//        }
//    }
//
//    public long addUser(UsersDataSet user) throws DBException {
//        try {
//            Session session = sessionFactory.openSession();
//            Transaction transaction = session.beginTransaction();
//            UsersDAO dao = new UsersDAO(session);
//            long id = dao.insertUser(user);
//            transaction.commit();
//            session.close();
//            return id;
//        } catch (HibernateException e) {
//            throw new DBException(e);
//        }
//    }
}
