package notebook.db;

import com.sun.istack.internal.Nullable;
import notebook.entity.EventDataSet;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

/**
 * Created by Максим on 07.04.2017.
 */
public class EventDAO {
    private Session session;

    public EventDAO(Session session) {
        this.session = session;
    }

    @Nullable
    public EventDataSet getById(long id) throws HibernateException {
        // If data doesn’t exists, it returns null.
        // Session.get throws org.hibernate.ObjectNotFoundException
        return session.get(EventDataSet.class, id);
    }

    @Nullable
    public List<EventDataSet> getAll() {
        return session.createQuery(
//                "select e from EventDataSet e", EventDataSet.class)
                "from EventDataSet", EventDataSet.class)
                .list();
    }

    @Nullable
    public List<EventDataSet> getAllSortByDate() {
        return session.createQuery(
                "select e from EventDataSet e order by date", EventDataSet.class)
//                "from EventDataSet", EventDataSet.class)
                .list();
    }

    @Nullable
    public List<EventDataSet> getAllSortByName() {
        return session.createQuery(
                "select e from EventDataSet e order by name", EventDataSet.class)
//                "from EventDataSet", EventDataSet.class)
                .list();
    }

    @Nullable
    public EventDataSet getByNameAndDate(String name, Date date) {
        return session.createQuery(
                "select e from EventDataSet e where e.name = :name and e.date = :date", EventDataSet.class)
                .setParameter("name", name)
                .setParameter("date", date)
                .uniqueResult();
    }

    public void insertEvent(EventDataSet event) throws HibernateException {
        session.persist(event); //better than session.save()
    }

    public void updateEvent(EventDataSet event) throws HibernateException {
        session.update(event);
    }

    public void deleteEvent(EventDataSet event) throws HibernateException {
        session.delete(event);// Hibernate
//        session.remove(event); JPA
    }

    public void deleteEventById(Long id) throws HibernateException {
        session.remove(
                getById(id)
                );
    }

}
