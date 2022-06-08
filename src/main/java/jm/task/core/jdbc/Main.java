package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserServiceImpl;
public class Main {
    public static void main(String[] args) {

        UserServiceImpl usi = new UserServiceImpl();
        usi.createUsersTable();
        usi.saveUser("Arthir", "Hayes", (byte) 38);
        usi.saveUser("Changpeng", "Zhao", (byte) 35);
        usi.saveUser("Michael", "Saylor", (byte) 41);
        usi.saveUser("Vitaly", "Buteri", (byte) 29);
        usi.removeUserById(4);
        usi.getAllUsers();
        usi.cleanUsersTable();
        usi.dropUsersTable();

    }
}