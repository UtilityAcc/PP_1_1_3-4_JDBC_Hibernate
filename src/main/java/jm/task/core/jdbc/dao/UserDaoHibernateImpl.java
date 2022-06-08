package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import java.util.List;
public class UserDaoHibernateImpl implements UserDao {
    private static final String CREATE_USERS_TABLE_SQL = "CREATE TABLE IF NOT EXISTS  %s ( id BIGINT not NULL AUTO_INCREMENT, name VARCHAR(255), lastName VARCHAR(255), age TINYINT, PRIMARY KEY ( id ))";
    private static final String DROP_USERS_TABLE_SQL = "drop table if exists ";
    private static final String CLEAR_USERS_TABLE_SQL = "DELETE FROM ";

    public UserDaoHibernateImpl() {

    }
    @Override
    public void createUsersTable() {
        Session session = null;
        try {
            User user = new User();
            session = Util.getSession();
            session.beginTransaction();
            session.createNativeQuery(String.format(CREATE_USERS_TABLE_SQL, user.getClass().getSimpleName())).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Created table in given database...");
        } finally {
            session.close();
        }
    }
    @Override
    public void dropUsersTable() {
        Session session = null;
        try {
            User user = new User();
            session = Util.getSession();
            session.beginTransaction();
            session.createNativeQuery(DROP_USERS_TABLE_SQL + user.getClass().getSimpleName()).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Dropped  table in given database...");
        } finally {
            session.close();
        }
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;
        try {
            User user = new User(name, lastName, age);
            session = Util.getSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            System.out.println("User with name: \"" + name + " " + lastName + "\" added to database...");
        } finally {
            session.close();
        }

    }

    @Override
    public void removeUserById(long id) {
        Session session = null;
        try {
            session = Util.getSession();
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
            System.out.println("Deleted user in given database, id " + id + "...");
        } finally {
            session.close();
        }
    }
    @Override
    public List < User > getAllUsers() {
        Session session = null;
        List < User > result;
        try {
            session = Util.getSession();
            session.beginTransaction();
            User user = new User();
            result = session.createQuery("FROM " + user.getClass().getSimpleName()).getResultList();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return result;
    }
    @Override
    public void cleanUsersTable() {
        Session session = null;
        try {
            User user = new User();
            session = Util.getSession();
            session.beginTransaction();
            session.createNativeQuery(CLEAR_USERS_TABLE_SQL + user.getClass().getSimpleName()).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Cleared table in given database...");
        } finally {
            session.close();
        }
    }
}