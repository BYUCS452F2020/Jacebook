package DAO.Graph;

import DAO.IStoryDAO;
import Model.Post;

import java.util.List;

public class GraphStoryDAO implements IStoryDAO {
    @Override
    public void addToStory(Post toAdd) {

    }

    @Override
    public List<Post> getStory(String alias) {
        return null;
    }

    @Override
    public List<Post> getStory(String alias, int pageSize, String lastPostID) {
        return null;
    }
}
