package DAO;

import Model.User;

public interface IUsersDAO {
    void addUser(String alias, String name, String photo, String password);
    void removeUser(String alias);
    void updateProfilePic(String alias, String url);
    User getUser(String alias);
    User verifyUser(String alias, String password);
    boolean aliasAvailable(String alias);
}
