package Model;

import java.util.List;

public class PostBundle {
    public Post post;
    public List<String> followers;

    public PostBundle(Post post, List<String> followers) {
        this.post = post;
        this.followers = followers;
    }
}
