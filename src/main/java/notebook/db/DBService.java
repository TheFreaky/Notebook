package notebook.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class DBService {
    private static final SessionFactory sessionFactory;

    static {
//        try {
//            Configuration configuration = new Configuration();
//            configuration.configure();
//
//            sessionFactory = configuration.buildSessionFactory();
//        } catch (Throwable ex) {
//            throw new ExceptionInInitializerError(ex);
//        }

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }

    public void close() {
        sessionFactory.close();
    }


//    public UsersDataSet getUser(long id) throws DBException {
//        try {
//            Session session = sessionFactory.openSession();
//            UsersDAO dao = new UsersDAO(session);
//            UsersDataSet dataSet = dao.getById(id);
//            session.close();
//            return dataSet;
//        } catch (HibernateException e) {
//            throw new DBException(e);
//        }
//    }
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
