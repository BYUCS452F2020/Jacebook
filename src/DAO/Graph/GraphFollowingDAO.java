package DAO.Graph;

import DAO.IFollowingDAO;

import java.util.List;

public class GraphFollowingDAO implements IFollowingDAO {
    @Override
    public void addFollowing(String alias, String followAlias) {

    }

    @Override
    public void removeFollowing(String alias, String unfollowAlias) {

    }

    @Override
    public List<String> getFollowing(String alias, Integer pageSize, String last) {
        return null;
    }

    @Override
    public boolean isFollowing(String alias, String followAlias) {
        return false;
    }
}
