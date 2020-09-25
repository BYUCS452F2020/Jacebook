package DAO;

import Model.Post;

import java.util.List;

public interface IHashtagDAO {
    void addHashtag(Post toAdd, String hashtag);

    List<Post> getHashtag(String hashtag);

    List<Post> getHashtag(String hashtag, int pageSize, String lastPostID);
}
