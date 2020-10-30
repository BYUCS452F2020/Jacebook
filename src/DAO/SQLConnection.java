package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {
    public static Connection conn;

    public static String _connectionURL ="jdbc:mysql://localhost:3306/Jacebook?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    public static Connection openConnection() {
        conn = null;
        String connectionURL = _connectionURL;

        try {
            conn = DriverManager.getConnection(connectionURL, "web", "web");
            conn.setAutoCommit(false);
            System.out.println("Connected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeConnection(boolean commit) {
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

    public static Connection getConn() {
        if(conn == null){
            return openConnection();
        }
        else return conn;
    }
}
