package DAO;

import java.sql.Timestamp;
import java.util.Date;
import java.sql.SQLException;
import java.util.UUID;
import java.sql.Connection;
import java.sql.PreparedStatement;



public class SQLAuthTokenDAO implements IAuthTokenDAO {

    private final Connection conn;

    public SQLAuthTokenDAO(Connection conn) {
        this.conn = conn;
    }

    public String addToken(String alias) {
        String token = UUID.randomUUID().toString();
        Date date = new Date();
        String timeStamp = new Timestamp(date.getTime()).toString();

        String sql = "INSERT INTO AuthToken (authToken, alias, timestamp) VALUES(?,?,?)";

        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            try {
                stmt.setString(1, token);
                stmt.setString(2, alias);
                stmt.setString(3, timeStamp);
                stmt.executeUpdate();
            } catch (Throwable var8) {
                if (stmt != null){
                    try {
                        stmt.close();
                    } catch (Throwable var7) {
                        var8.addSuppressed(var7);
                    }
                }
                throw var8;
            }
            if (stmt != null){
                stmt.close();
            }
        } catch (SQLException var9){
            var9.printStackTrace();
        }
        return token;
        // DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        // Date date = new Date();
        //return UUID.randomUUID().toString();
    }

    public void removeToken(String authToken) {
        String sql = "DELETE FROM AuthToken WHERE authToken = ?;";
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            try {
                stmt.setString(1, authToken);
                stmt.executeUpdate();
            } catch (Throwable var7) {
                if (stmt != null) {
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

    public String authenticateToken(String authToken) {
        return "JKandy";
    }
}
