package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLInitTables {

    static {
        try {
            final String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection conn;

    public void openConnection() {
        conn = null;
        String connectionURL = "jdbc:mysql://localhost:3306/Jacebook?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

        try {
            conn = DriverManager.getConnection(connectionURL, "web", "web");
            conn.setAutoCommit(false);
            System.out.println("Connected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(boolean commit) {
        try {
            if (commit) {
                conn.commit();
            }
            else {
                conn.rollback();
            }
            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearTables() {
        openConnection();

        try (Statement stmt = conn.createStatement()){
            String person = "DELETE FROM person";
            stmt.executeUpdate(person);
            closeConnection(true);
        } catch (SQLException e) {
            closeConnection(false);
        }
    }


    public void createTables() {
        openConnection();

        //table definitions
        StringBuilder users = new StringBuilder();
        users.append("create table if not exists Users ( ");
        users.append("alias varchar(255) not null primary key, ");
        users.append("name varchar(255) not null, ");
        users.append("photo varchar(255) ");
        users.append(");");


        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate(users.toString());
                closeConnection(true);
            } finally {
                if (stmt != null) stmt.close();
            }
        } catch (SQLException e) {
            closeConnection(false);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SQLInitTables init = new SQLInitTables();
        init.createTables();
    }
}
