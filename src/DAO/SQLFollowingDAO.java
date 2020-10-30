package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class SQLFollowingDAO implements IFollowingDAO {
    private final Connection conn;

    public SQLFollowingDAO(Connection conn) {
        this.conn = conn;
    }

    public void addFollowing(String alias, String followAlias) {
        String followingID = UUID.randomUUID().toString();
        String sql = "INSERT INTO Following (followingID, userAlias, followingAlias) VALUES(?,?,?)";
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);

            try {
                stmt.setString(1, followingID);
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

    public void removeFollowing(String alias, String unfollowAlias){
        String sql = "DELETE FROM Following WHERE userAlias = ? AND followingAlias = ?;";

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

    public List<String> getFollowing(String alias, Integer pageSize, String last){
        return;
    }

    public boolean isFollowing(String alias, String followAlias) {
        return false;
    }
}
