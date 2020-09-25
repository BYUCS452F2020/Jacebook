package Context.GetProfile;

import Context.HttpUtils;
import Context.SignIn.SignInResponse;
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

public class GetProfile implements HttpHandler {
    private Gson g;
    private IUsersDAO usersDAO;

    // constructor initializes data access objects and gson
    public GetProfile() {
        g = new Gson();
        usersDAO = new SQLUsersDAO();
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

            String username = exchange.getRequestURI().toString().split("/|\\?")[2];
            if (username == null || username.equals("")) send400(exchange, "Please include a username");

            User user = usersDAO.getUser(username);
            if (user == null) send400(exchange, "Account not found");

            send200(exchange, user);


        }
        catch (IOException e) {
            send500(exchange);
        }

    }

    // handles 200 responses
    private void send200(HttpExchange exchange, User user) throws IOException {
        GetProfileResponse resp = new GetProfileResponse();
        resp.alias = user.alias;
        resp.name = user.name;
        resp.photo = user.photo;

        exchange.sendResponseHeaders(200, 0);
        OutputStream respBody = exchange.getResponseBody();
        HttpUtils.writeString(g.toJson(resp), respBody);
        respBody.close();
    }

    // handles 400 responses with custom message
    private void send400(HttpExchange exchange, String message) throws IOException {
        GetProfileResponse resp = new GetProfileResponse();
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
