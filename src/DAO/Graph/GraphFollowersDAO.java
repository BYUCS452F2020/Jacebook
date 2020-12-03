package DAO.Graph;

import DAO.IFollowersDAO;

import java.util.List;

public class GraphFollowersDAO implements IFollowersDAO {
    @Override
    public void addFollower(String alias, String followAlias) {

    }

    @Override
    public void removeFollower(String alias, String unfollowAlias) {

    }

    @Override
    public List<String> getFollowers(String alias) {
        return null;
    }

    @Override
    public List<String> getFollowers(String alias, Integer pageSize, String last) {
        return null;
    }
}
