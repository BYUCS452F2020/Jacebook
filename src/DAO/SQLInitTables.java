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

    private void runSQL(String runStatement){
        openConnection();

        try{
            Statement stmt = null;
            try{
                stmt = conn.createStatement();
                stmt.executeUpdate(runStatement.toString());
                closeConnection(true);
            } finally{
                if (stmt != null) stmt.close();
            }
        }catch (SQLException e){
            closeConnection(false);
            e.printStackTrace();
        }
    }

    public void createTables() {
        openConnection();

        //table definitions
        String users = "create table if not exists Users ( " +
                "alias varchar(255) not null primary key, " +
                "name varchar(255) not null, " +
                "photo varchar(255)); ";
        runSQL(users);

        String followers = "CREATE TABLE IF NOT EXISTS FOLLOWERS " +
                "(followersID VARCHAR(255) not NULL, " +
                "userAlias VARCHAR(255) not NULL, " +
                "followerAlias VARCHAR(255) not NULL, " +
                "PRIMARY KEY (followersID), " +
                "FOREIGN KEY (userAlias) REFERENCES Users(alias), " +
                "FOREIGN KEY (followerAlias) REFERENCES Users(alias));";
        runSQL(followers);

        String following = "CREATE TABLE IF NOT EXISTS FOLLOWERS " +
                "(followingID VARCHAR(255) not NULL, " +
                "userAlias VARCHAR(255) not NULL, " +
                "followingAlias VARCHAR(255) not NULL, " +
                "PRIMARY KEY (followersID), " +
                "FOREIGN KEY (userAlias) REFERENCES Users(alias), " +
                "FOREIGN KEY (followerAlias) REFERENCES Users(alias));";
        runSQL(following);

        String authToken = "CREATE TABLE IF NOT EXISTS AuthToken " +
                "(AuthToken VARCHAR(255) NOT NULL UNIQUE, " +
                "Alias VARCHAR(255) NOT NULL, " +
                "Timestamp VARCHAR(255) NOT NULL, " +
                "PRIMARY KEY (AuthToken), " +
                "FOREIGN KEY (Alias) REFERENCES Users(Alias));";
        runSQL(authToken);

        String feed = "Create TABLE IF NOT EXISTS Feed " +
                "(FeedID VARCHAR(255) NOT NULL UNIQUE, " +
                "Alias VARCHAR(255) NOT NULL, " +
                "PostID VARCHAR(255) NOT NULL, " +
                "PRIMARY KEY (FeedID), " +
                "FOREIGN KEY (Alias) REFERENCES Users(Alias), " +
                "FOREIGN KEY (PostID) REFERENCES Posts(PostID));";
        runSQL(feed);

    }

    public static void main(String[] args) {
        SQLInitTables init = new SQLInitTables();
        init.createTables();
    }
}
