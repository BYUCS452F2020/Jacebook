package DAO;

import Model.Post;

import java.util.List;

public interface IFeedDAO {
    void addToFeed(Post toAdd, String alias);

    void batchAddToFeed(Post toAdd, List<String> aliases);

    List<Post> getFeed(String alias);

    List<Post> getFeed(String alias, int pageSize, String lastPostID);
}
