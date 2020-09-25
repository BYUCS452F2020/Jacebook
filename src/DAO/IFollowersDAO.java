package DAO;

import java.util.List;

public interface IFollowersDAO {
    void addFollower(String alias, String followAlias);

    void removeFollower(String alias, String unfollowAlias);

    List<String> getFollowers(String alias);

    List<String> getFollowers(String alias, Integer pageSize, String last);
}
