package DAO;

import Model.User;

import java.sql.Connection;

public class SQLUsersDAO implements IUsersDAO {
    private final Connection conn;

    public SQLUsersDAO(Connection conn) {
        this.conn = conn;
    }

    public void addUser(String alias, String name, String photo, String password) {
        return;
    }

    public void removeUser(String alias) {
        return;
    }

    public void updateProfilePic(String alias, String url) {
        return;
    }

    public User getUser(String alias) {
        return new User(alias, "Vader", "https://pbs.twimg.com/profile_images/3103894633/e0d179fc5739a20308331b432e4f3a51.jpeg");
    }

    public User verifyUser(String alias, String password) {
        return new User(alias, "Vader", "https://pbs.twimg.com/profile_images/3103894633/e0d179fc5739a20308331b432e4f3a51.jpeg");
    }

    public boolean aliasAvailable(String alias) {
        return false;
    }
}
