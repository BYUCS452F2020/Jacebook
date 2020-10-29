package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLInitTables {

    private static String _connectionURL ="jdbc:mysql://localhost:3306/Jacebook?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

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
        String connectionURL = _connectionURL;

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

    public void runSQL(String runStatement){
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

        String hashtag = "CREATE TABLE IF NOT EXISTS Hashtags (" +
                "hashtagID varchar(255) not NULL, " +
                "hashtag varchar(255) not NULL, " +
                "postID varchar(255) not NULL, " +
                "PRIMARY KEY (hashtagID), " +
                "FOREIGN KEY (postID) REFERENCES Posts(postID));";
        runSQL(hashtag);

        String posts = "CREATE TABLE IF NOT EXISTS Posts (" +
                "postID varchar(255) not NULL, " +
                "alias varchar(255) not NULL, " +
                "content varchar(255) not NULL, " +
                "timestamp varchar(255) not NULL, " +
                "image varchar(255), " +
                "video varchar(255), " +
                "PRIMARY KEY (postID), " +
                "FOREIGN KEY (alias) REFERENCES Users(alias));";
        runSQL(posts);

        String followers = "CREATE TABLE IF NOT EXISTS Followers " +
                "(followersID VARCHAR(255) not NULL, " +
                "userAlias VARCHAR(255) not NULL, " +
                "followerAlias VARCHAR(255) not NULL, " +
                "PRIMARY KEY (followersID), " +
                "FOREIGN KEY (userAlias) REFERENCES Users(alias), " +
                "FOREIGN KEY (followerAlias) REFERENCES Users(alias));";
        runSQL(followers);

        String following = "CREATE TABLE IF NOT EXISTS Following " +
                "(followingID VARCHAR(255) not NULL, " +
                "userAlias VARCHAR(255) not NULL, " +
                "followingAlias VARCHAR(255) not NULL, " +
                "PRIMARY KEY (followersID), " +
                "FOREIGN KEY (userAlias) REFERENCES Users(alias), " +
                "FOREIGN KEY (followerAlias) REFERENCES Users(alias));";
        runSQL(following);

        String authToken = "CREATE TABLE IF NOT EXISTS AuthToken " +
                "(authToken VARCHAR(255) NOT NULL UNIQUE, " +
                "alias VARCHAR(255) NOT NULL, " +
                "timestamp VARCHAR(255) NOT NULL, " +
                "PRIMARY KEY (authToken), " +
                "FOREIGN KEY (alias) REFERENCES Users(alias));";
        runSQL(authToken);

        String feed = "Create TABLE IF NOT EXISTS Feed " +
                "(feedID VARCHAR(255) NOT NULL UNIQUE, " +
                "alias VARCHAR(255) NOT NULL, " +
                "postID VARCHAR(255) NOT NULL, " +
                "PRIMARY KEY (feedID), " +
                "FOREIGN KEY (alias) REFERENCES Users(alias), " +
                "FOREIGN KEY (postID) REFERENCES Posts(postID));";
        runSQL(feed);

    }

    public static void main(String[] args) {
        SQLInitTables init = new SQLInitTables();
        init.createTables();
    }
}
