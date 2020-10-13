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
        StringBuilder person = new StringBuilder();
        person.append("create table if not exists person ( ");
        person.append("person_id varchar(255) not null primary key, ");
        person.append("descendant varchar(255) not null, ");
        person.append("first_name varchar(255) not null, ");
        person.append("last_name varchar(255) not null, ");
        person.append("gender varchar(1) not null, ");
        person.append("father varchar(255), ");
        person.append("mother varchar(255), ");
        person.append("spouse varchar(255), ");
        person.append("constraint ck_gender check (gender in ('m', 'f', '?')) ");
        person.append(");");



        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate(person.toString());
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
