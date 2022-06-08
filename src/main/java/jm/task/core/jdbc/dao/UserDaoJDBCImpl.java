package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {

    private final Connection connection = Util.getConnection();

    private static final  String CREATE_USERS_TABLE_SQL = "CREATE TABLE IF NOT EXISTS  User ( id BIGINT not NULL AUTO_INCREMENT, name VARCHAR(255), lastName VARCHAR(255), age TINYINT, PRIMARY KEY ( id ))";
    private static final  String DROP_USERS_TABLE_SQL = "DROP TABLE IF EXISTS User";
    private static final  String ADD_STUDENT_SQL = """
            INSERT INTO User (name, lastName,age ) 
            VALUES(?,?,?)""";
    private static final String DELETE_BY_ID_SQL = """
            DELETE FROM User
            WHERE id = ?
            """;
    private static final  String SHOW_ALL_SQL = """
            SELECT id,
            name,
            lastName,
            age
            FROM User
            """;
    private static final  String CLEAR_USERS_TABLE_SQL = "DELETE FROM User";


    public UserDaoJDBCImpl() {

    }
    public void createUsersTable() {
        try (Statement statement = connection.createStatement();) {
            statement.executeUpdate(CREATE_USERS_TABLE_SQL);
            System.out.println("Created table in given database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void dropUsersTable() {
        try (Statement statement = connection.createStatement();) {
            statement.executeUpdate(DROP_USERS_TABLE_SQL);
            System.out.println("Dropped  table in given database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement = connection.prepareStatement(ADD_STUDENT_SQL);) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("User with name: \"" + name + " " + lastName + "\" added to database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void removeUserById(long id) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_SQL);) {
            statement.setLong(1, id);
            statement.executeUpdate();
            System.out.println("Deleted user in given database, id " + id + "...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List < User > getAllUsers() {
        List < User > users = new ArrayList < > ();
        try (PreparedStatement statement = connection.prepareStatement(SHOW_ALL_SQL);) {
            ResultSet queryResult = statement.executeQuery();
            while (queryResult.next()) {
                User tmpUser = new User();
                tmpUser.setId(queryResult.getLong("id"));
                tmpUser.setName(queryResult.getString("name"));
                tmpUser.setLastName(queryResult.getString("lastName"));
                tmpUser.setAge(queryResult.getByte("age"));
                users.add(tmpUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement();) {
            statement.executeUpdate(CLEAR_USERS_TABLE_SQL);
            System.out.println("Cleared table in given database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}