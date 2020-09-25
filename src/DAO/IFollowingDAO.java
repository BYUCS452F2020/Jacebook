package DAO;

import java.util.List;

public interface IFollowingDAO {
    void addFollowing(String alias, String followAlias);

    void removeFollowing(String alias, String unfollowAlias);

    List<String> getFollowing(String alias, Integer pageSize, String last);

    boolean isFollowing(String alias, String followAlias);

}
