package DAO;

import java.util.UUID;

public class SQLAuthTokenDAO implements IAuthTokenDAO {
    public String addToken(String alias) {
        // DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        // Date date = new Date();
        return UUID.randomUUID().toString();
    }

    public void removeToken(String authToken) {
        return;
    }

    public String authenticateToken(String authToken) {
        return "JKandy";
    }
}
