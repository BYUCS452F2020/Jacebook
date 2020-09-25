package DAO;

import Model.Post;

public interface IPostsDAO {
    void addPost(Post toAdd);
    Post getPost(String postID);
}
