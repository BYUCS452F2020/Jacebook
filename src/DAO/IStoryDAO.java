package DAO;

import Model.Post;

import java.util.*;

public interface IStoryDAO {
     void addToStory(Post toAdd);
    List<Post> getStory(String alias);
    List<Post> getStory(String alias, int pageSize, String lastPostID);
}
