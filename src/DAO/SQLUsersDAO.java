package DAO;

import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLUsersDAO implements IUsersDAO {
    private final Connection conn;

    public SQLUsersDAO(Connection conn) {
        this.conn = conn;
    }

    public SQLUsersDAO(){
        this.conn = SQLConnection.getConn();
    }

    public void addUser(String alias, String name, String photo, String password) {
        String sql = "INSERT INTO Users (alias, name, photo) VALUES(?,?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, alias);
            stmt.setString(2, name);
            stmt.setString(3, photo);

            stmt.executeUpdate();
            SQLConnection.closeConnection(true);
        } catch (SQLException e) {
            SQLConnection.closeConnection(false);
            e.printStackTrace();
            //throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    public void removeUser(String alias) {
        String sql = "DELETE FROM Users WHERE alias = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, alias);
            stmt.executeUpdate();
            SQLConnection.closeConnection(true);
        } catch (SQLException e) {
            SQLConnection.closeConnection(false);
            e.printStackTrace();
            //throw new DataAccessException("SQL Error encountered while clearing tables");
        }
    }

    public void updateProfilePic(String alias, String url) {
        String sql = "UPDATE Users SET photo = ? WHERE alias = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, url);
            stmt.setString(2, alias);
            stmt.executeUpdate();
            SQLConnection.closeConnection(true);
        } catch (SQLException e) {
            SQLConnection.closeConnection(false);
            e.printStackTrace();
            //throw new DataAccessException("SQL Error encountered while clearing tables");
        }
    }

    public User getUser(String alias) {
        return new User(alias, "Vader", "https://pbs.twimg.com/profile_images/3103894633/e0d179fc5739a20308331b432e4f3a51.jpeg");
    }

    public User verifyUser(String alias, String password) {
        return new User(alias, "Vader", "https://pbs.twimg.com/profile_images/3103894633/e0d179fc5739a20308331b432e4f3a51.jpeg");
    }

    public boolean aliasAvailable(String alias) {
        ResultSet rs = null;
        String sql = "SELECT COUNT(alias) FROM Users WHERE alias = ? ";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, alias);
            rs = stmt.executeQuery();
            if (rs.next()){
                return rs.getInt("COUNT(alias)") == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //throw new DataAccessException("Error encountered while finding posts");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return false;
    }
}
