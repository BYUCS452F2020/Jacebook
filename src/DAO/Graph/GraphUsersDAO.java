package DAO.Graph;

import Context.HttpUtils;
import DAO.Graph.Model.AddTokenRequest;
import DAO.Graph.Model.AddUserRequest;
import DAO.Graph.Model.RemoveUserRequest;
import DAO.IUsersDAO;
import Model.Post;
import Model.User;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.UUID;

public class GraphUsersDAO implements IUsersDAO {
    @Override
    public void addUser(String alias, String name, String photo, String password) {
        Gson g = new Gson();
        AddUserRequest req = new AddUserRequest();
        req.alias = alias;
        req.name = name;
        req.photo = photo;
        req.salt = password;
        req.hash = Integer.toString(password.hashCode());
        try {
            URL url = new URL("http://localhost:8080/sky/event/" + Graph.eci + "/java/jacebook/add_user");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            HttpUtils.writeString(g.toJson(req), conn.getOutputStream());
            conn.getOutputStream().close();
            conn.connect();
            System.out.println(HttpUtils.readString(conn.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUser(String alias) {
        Gson g = new Gson();
        RemoveUserRequest req = new RemoveUserRequest();
        req.alias = alias;
        try {
            URL url = new URL("http://localhost:8080/sky/event/" + Graph.eci + "/java/jacebook/remove_user");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            HttpUtils.writeString(g.toJson(req), conn.getOutputStream());
            conn.getOutputStream().close();
            conn.connect();
            System.out.println(HttpUtils.readString(conn.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateProfilePic(String alias, String url) {

    }

    @Override
    public User getUser(String alias) {
        Gson g = new Gson();
        User response = null;
        try {
            URL url = new URL("http://localhost:8080/sky/event/" + Graph.eci + "/java/jacebook/get_user?alias=" + alias);
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
    public User verifyUser(String alias, String password) {
        return null;
    }

    @Override
    public boolean aliasAvailable(String alias) {
        Gson g = new Gson();
        Boolean response = null;
        try {
            URL url = new URL("http://localhost:8080/sky/event/" + Graph.eci + "/java/jacebook/alias_available?alias=" + alias);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            HttpUtils.writeString(g.toJson(alias), conn.getOutputStream());
            conn.getOutputStream().close();
            conn.connect();
            System.out.println();
            return (boolean) g.fromJson(HttpUtils.readString(conn.getInputStream()),response.getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
