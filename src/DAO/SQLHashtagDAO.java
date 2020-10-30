package DAO;

import Model.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class SQLHashtagDAO implements IHashtagDAO {
    private Connection conn;

    public SQLHashtagDAO(Connection conn) {
        this.conn = conn;
    }

    public SQLHashtagDAO(){
        this.conn = SQLConnection.getConn();
    }
    @Override
    public void addHashtag(Post toAdd, String hashtag) {
        String sql = "INSERT INTO Hashtags (hashtagID, hashtag, postID) VALUES(?,?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, UUID.randomUUID().toString());
            stmt.setString(2, hashtag);
            stmt.setString(3, toAdd.id);

            stmt.executeUpdate();
            SQLConnection.closeConnection(true);
        } catch (SQLException e) {
            SQLConnection.closeConnection(false);
            e.printStackTrace();
            //throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    @Override
    public List<Post> getHashtag(String hashtag) {
        List<Post> posts = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT Posts.*, Hashtags.hashtag" +
                "JOIN Hashtags on Posts.postID = Hashtags.postID" +
                "WHERE hashtag = ? " +
                "GROUP BY Posts.postID;" ;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, hashtag);
            rs = stmt.executeQuery();
            while (rs.next()) {
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

                Post post = new Post(userAlias,content,postID,name,new ArrayList<String>(),hashtags,new ArrayList<String>(),timestamp,image,video,photo);
                posts.add(post);
            }
            return posts;
        } catch (SQLException e) {
            e.printStackTrace();
            //throw new DataAccessException("Error encountered while finding posts");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
}

    @Override
    public List<Post> getHashtag(String hashtag, int pageSize, String lastPostID) {
        return null;
    }
}
