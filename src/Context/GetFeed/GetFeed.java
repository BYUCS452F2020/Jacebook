package Context.GetFeed;

import Context.HttpUtils;
import DAO.*;
import DAO.SQL.SQLFeedDAO;
import DAO.SQL.SQLUsersDAO;
import Model.Post;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class GetFeed implements HttpHandler {

    private Gson g;
    private IUsersDAO usersDAO;
    private IFeedDAO feedDao;

    // constructor initializes data access objects and gson
    public GetFeed() {
        g = new Gson();
        usersDAO = new SQLUsersDAO();
        feedDao = new SQLFeedDAO(conn);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            Headers reqHeaders = exchange.getRequestHeaders();
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

            if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, OPTIONS");
                exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type,Authorization");
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            String username = exchange.getRequestURI().toString().split("/|\\?")[2];
            if(usersDAO.getUser(username) == null) {
                send400(exchange, "User does not exist");
                return;
            }
            List<Post> feed = feedDao.getFeed(username);
            send200(exchange, feed);

        }
        catch (IOException e) {
            send500(exchange);
        }

    }

    // handles 200 responses
    private void send200(HttpExchange exchange, List<Post> posts) throws IOException {
        GetFeedResponse resp = new GetFeedResponse();
        resp.posts = posts;

        exchange.sendResponseHeaders(200, 0);
        OutputStream respBody = exchange.getResponseBody();
        HttpUtils.writeString(g.toJson(resp), respBody);
        respBody.close();
    }

    // handles 400 responses with custom message
    private void send400(HttpExchange exchange, String message) throws IOException {
        GetFeedResponse resp = new GetFeedResponse();
        resp.message = message;

        exchange.sendResponseHeaders(400, 0);
        OutputStream respBody = exchange.getResponseBody();
        HttpUtils.writeString(g.toJson(resp), respBody);
        respBody.close();
    }

    // handles any unhandled errors on our side
    private void send500(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(500, 0);
        exchange.getResponseBody().close();
    }
}
