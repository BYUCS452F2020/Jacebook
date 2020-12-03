package DAO.SQL;

import DAO.IFeedDAO;
import Model.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class SQLFeedDAO implements IFeedDAO {
    private final Connection conn;

    public SQLFeedDAO(Connection conn) {
        this.conn = conn;
    }

    public void addToFeed(Post toAdd, String alias) {
        String feedID = UUID.randomUUID().toString();
        String sql = "INSERT INTO Feed (feedID, alias, postID) VALUES(?,?,?)";
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);

            try {
                stmt.setString(1, feedID);
                stmt.setString(2, alias);
                stmt.setString(3, toAdd.id);
                stmt.executeUpdate();
            } catch (Throwable var7) {
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (Throwable var6) {
                        var7.addSuppressed(var6);
                    }
                }
                throw var7;
            }
            if (stmt != null){
                stmt.close();
            }
        } catch (SQLException var8){
            var8.printStackTrace();
        }
    }

    public void batchAddToFeed(Post toAdd, List<String> aliases) {
        return;
    }

    public List<Post> getFeed(String alias) {
        List posts = new ArrayList<Post>();
        ResultSet rs = null;
        String sql = "SELECT Posts.*, Feed.alias " +
                "JOIN Feed on Posts.postID = Feed.postID " +
                "WHERE alias = ? " +
                "GROUP BY Posts.postID;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, alias);
            rs = stmt.executeQuery();
            while (rs.next()){
                String userAlias = rs.getString("Posts.alias");
                String content = rs.getString("content");
                String postID = rs.getString("postID");
                String name = rs.getString("name");
                String timestamp = rs.getString("timestamp");
                String image = rs.getString("image");
                String video = rs.getString("video");
                String photo = rs.getString("photo");
                String hashtagString = rs.getString("hashtags");
                List<String> hashtags = Arrays.asList(hashtagString.split("\\s*,\\s*"));

                Post post = new Post(userAlias, content, postID, name, new ArrayList<String>(), hashtags, new ArrayList<String>(), timestamp, image, video, photo);
                posts.add(post);
            }
            return posts;
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
          if (rs != null) {
              try {
                  rs.close();
              } catch (SQLException e){
                  e.printStackTrace();
              }
          }
        }
        return null;
    }

    public List<Post> getFeed(String alias, int pageSize, String lastPostID) {
        return getFeed("");
    }
}
