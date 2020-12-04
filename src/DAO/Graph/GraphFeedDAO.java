package DAO.Graph;

import Context.HttpUtils;
import DAO.IFeedDAO;
import Model.Post;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class GraphFeedDAO implements IFeedDAO {
    @Override
    public void addToFeed(Post toAdd, String alias) {
    }

    @Override
    public void batchAddToFeed(Post toAdd, List<String> aliases) {
    }

    @Override
    public List<Post> getFeed(String alias) {
        Gson g = new Gson();
        List<Post> response = null;
        try {
            URL url = new URL("http://localhost:8080/sky/event/" + Graph.eci + "/java/jacebook/get_feed?alias=" + alias);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            HttpUtils.writeString(g.toJson(alias), conn.getOutputStream());
            conn.getOutputStream().close();
            conn.connect();
            System.out.println();
            return (List<Post>) g.fromJson(HttpUtils.readString(conn.getInputStream()),response.getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Post> getFeed(String alias, int pageSize, String lastPostID) {
        return getFeed(alias);
    }
}
