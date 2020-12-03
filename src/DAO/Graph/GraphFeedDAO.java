package DAO.Graph;

import DAO.IFeedDAO;
import Model.Post;

import java.util.List;

public class GraphFeedDAO implements IFeedDAO {
    @Override
    public void addToFeed(Post toAdd, String alias) {

    }

    @Override
    public void batchAddToFeed(Post toAdd, List<String> aliases) {

    }

    @Override
    public List<Post> getFeed(String alias) {
        return null;
    }

    @Override
    public List<Post> getFeed(String alias, int pageSize, String lastPostID) {
        return null;
    }
}
