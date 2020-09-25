package DAO;

import Model.Post;

import java.util.ArrayList;
import java.util.List;

public class SQLFeedDAO implements IFeedDAO {
    public void addToFeed(Post toAdd, String alias) {
        return;
    }

    public void batchAddToFeed(Post toAdd, List<String> aliases) {
        return;
    }

    public List<Post> getFeed(String alias) {
        List posts = new ArrayList<Post>();
        posts.add(new Post("WannabeBruce", "Trump 2020!", "id", "Norbert Martin", null, null, null, "Now", "null", "null", "https://s3.amazonaws.com/gunmemorial-media/photo/51004.jpg"));
        posts.add(new Post("FratBoiBlack", "Idk man is logic really black tho?", "id2", "Miguel Negro", null, null, null, "Yesterday", "https://d.newsweek.com/en/full/794511/logic-rapper.jpg?w=1600&h=1200&q=88&f=5a61037cd591eae2e66e1158dd564bd8", "null", "https://cdn.shopify.com/s/files/1/1740/0489/products/SUMMER_WEIGHT_PORCH_WHITE_620x.jpg?v=1581466432"));
        return posts;
    }

    public List<Post> getFeed(String alias, int pageSize, String lastPostID) {
        return getFeed("");
    }
}
