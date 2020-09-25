package DAO;

public interface IAuthTokenDAO {
    String addToken(String alias);

    void removeToken(String authToken);

    String authenticateToken(String authToken);
}
