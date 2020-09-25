package Context.SignIn;

import Context.HttpUtils;
import DAO.IAuthTokenDAO;
import DAO.IUsersDAO;
import DAO.SQLAuthTokenDAO;
import DAO.SQLUsersDAO;
import Model.User;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class SignIn implements HttpHandler {

    private Gson g;
    private IUsersDAO usersDAO;
    private IAuthTokenDAO authTokenDAO;

    // constructor initializes data access objects and gson
    public SignIn() {
        g = new Gson();
        usersDAO = new SQLUsersDAO();
        authTokenDAO = new SQLAuthTokenDAO();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            Headers reqHeaders = exchange.getRequestHeaders();
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

            if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, OPTIONS");
                exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type,Authorization,username,password");
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            if (!reqHeaders.containsKey("username") || !reqHeaders.containsKey("password")) {
                send400(exchange, "Please include username and password");
                return;
            }

            String username = reqHeaders.getFirst("username");
            String password = reqHeaders.getFirst("password");

            User user = usersDAO.verifyUser(username, password);

            if (user == null) {
                send400(exchange, "Username and password don't match");
                return;
            }

            String authToken = authTokenDAO.addToken(username);
            send200(exchange, user, authToken);
        }
        catch (IOException e) {
            send500(exchange);
        }

    }

    // handles 200 responses
    private void send200(HttpExchange exchange, User user, String authToken) throws IOException {
        SignInResponse resp = new SignInResponse();
        resp.authToken = authToken;
        resp.photo = user.photo;

        exchange.sendResponseHeaders(200, 0);
        OutputStream respBody = exchange.getResponseBody();
        HttpUtils.writeString(g.toJson(resp), respBody);
        respBody.close();
    }

    // handles 400 responses with custom message
    private void send400(HttpExchange exchange, String message) throws IOException {
        SignInResponse resp = new SignInResponse();
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