package DAO.Graph;

import DAO.IUsersDAO;
import Model.User;

public class GraphUsersDAO implements IUsersDAO {
    @Override
    public void addUser(String alias, String name, String photo, String password) {
        
    }

    @Override
    public void removeUser(String alias) {

    }

    @Override
    public void updateProfilePic(String alias, String url) {

    }

    @Override
    public User getUser(String alias) {
        return null;
    }

    @Override
    public User verifyUser(String alias, String password) {
        return null;
    }

    @Override
    public boolean aliasAvailable(String alias) {
        return false;
    }
}
