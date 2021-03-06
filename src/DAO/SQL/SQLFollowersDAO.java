package DAO.SQL;

import DAO.IFollowersDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SQLFollowersDAO implements IFollowersDAO {
    private final Connection conn;

    public SQLFollowersDAO(Connection conn) {
        this.conn = conn;
    }

    public void addFollower(String alias, String followAlias){
        String followerID = UUID.randomUUID().toString();;
        String sql = "INSERT INTO Followers (followersID, userAlias, followerAlias) VALUES(?,?,?)";
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);

            try {
                stmt.setString(1, followerID);
                stmt.setString(2, alias);
                stmt.setString(3, followAlias);
                stmt.executeUpdate();
            } catch (Throwable var7) {
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (Throwable var6) {
                        var7.addSuppressed(var6);
                    }
                }
                throw var7;
            }
            if (stmt != null){
                stmt.close();
            }
        } catch (SQLException var8){
            var8.printStackTrace();
        }
    }

    public void removeFollower(String alias, String unfollowAlias){
        String sql = "DELETE FROM Followers WHERE userAlias = ? AND followerAlias = ?;";

        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);

            try {
                stmt.setString(1, alias);
                stmt.setString(2, unfollowAlias);
                stmt.executeUpdate();
            } catch (Throwable var7) {
                if (stmt != null){
                    try {
                        stmt.close();
                    } catch (Throwable var6){
                        var7.addSuppressed(var6);
                    }
                }
                throw var7;
            }
            if (stmt != null){
                stmt.close();
            }
        } catch (SQLException var8){
            var8.printStackTrace();
        }
    }

    public List<String> getFollowers(String alias){
        List<String> followers = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT Followers.* FROM Users " +
                "JOIN Followers on Users.alias = Followers.userAlias " +
                "WHERE Users.alias = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, alias);
            rs = stmt.executeQuery();
            while (rs.next()){
                followers.add(rs.getString("followerAlias"));
            }
            return followers;
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (rs != null){
                try{
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public List<String> getFollowers(String alias, Integer pageSize, String last){
        return null;
    }
}
