package DAO.Graph;

import Context.HttpUtils;
import DAO.IStoryDAO;
import Model.Post;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class GraphStoryDAO implements IStoryDAO {
    @Override
    public void addToStory(Post toAdd) {

    }

    @Override
    public List<Post> getStory(String alias) {
        Gson g = new Gson();
        List<Post> response = null;
        try {
            URL url = new URL("http://localhost:8080/sky/event/" + Graph.eci + "/java/jacebook/get_story?alias=" + alias);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            HttpUtils.writeString(g.toJson(alias), conn.getOutputStream());
            conn.getOutputStream().close();
            conn.connect();
            System.out.println();
            assert response != null;
            return g.fromJson(HttpUtils.readString(conn.getInputStream()),response.getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Post> getStory(String alias, int pageSize, String lastPostID) {
        return getStory(alias);
    }
}
