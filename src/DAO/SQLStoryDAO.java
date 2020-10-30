package DAO;

import Model.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SQLStoryDAO implements IStoryDAO {
    private Connection conn;

    public SQLStoryDAO(Connection conn) {
        this.conn = conn;
    }

    public SQLStoryDAO(){
        this.conn = SQLConnection.getConn();
    }

    @Override
    public void addToStory(Post toAdd) {
        String sql = "INSERT INTO Posts (postID, alias, content, timestamp, image, video) " +
                "VALUES(?,?,?,?,?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, toAdd.id);
            stmt.setString(2, toAdd.alias);
            stmt.setString(3, toAdd.content);
            stmt.setString(4, toAdd.timestamp);
            stmt.setString(5, toAdd.image);
            stmt.setString(6, toAdd.video);

            stmt.executeUpdate();
            SQLConnection.closeConnection(true);
        } catch (SQLException e) {
            SQLConnection.closeConnection(false);
             e.printStackTrace();
            //throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    @Override
    public List<Post> getStory(String alias) {
        List<Post> posts = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT Posts.*, Users.name , group_concat(Hashtags.hashtag) as hashtags" +
                "FROM Posts JOIN Users ON Posts.alias = Users.alias " +
                "JOIN Hashtags on Posts.postID = Hashtags.postID" +
                "WHERE Posts.alias = ? " +
                "GROUP by Posts.postID;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, alias);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String userAlias = rs.getString("Posts.alias");
                String content = rs.getString("content");
                String postID = rs.getString("Posts.postID");
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
    public List<Post> getStory(String alias, int pageSize, String lastPostID) {
        return null;
    }
}
