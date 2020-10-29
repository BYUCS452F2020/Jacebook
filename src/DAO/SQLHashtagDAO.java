package DAO;

import Model.Post;

import java.util.List;

public class SQLHashtagDAO implements IHashtagDAO {

    @Override
    public void addHashtag(Post toAdd, String hashtag) {

    }

    @Override
    public List<Post> getHashtag(String hashtag) {
        return null;
    }

    @Override
    public List<Post> getHashtag(String hashtag, int pageSize, String lastPostID) {
        return null;
    }
}
