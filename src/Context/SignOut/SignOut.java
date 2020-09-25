package Context.SignOut;

import Context.HttpUtils;
import DAO.IAuthTokenDAO;
import DAO.SQLAuthTokenDAO;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class SignOut implements HttpHandler {

    private Gson g;
    private IAuthTokenDAO authTokenDAO;

    // constructor initializes data access objects and gson
    public SignOut() {
        g = new Gson();
        authTokenDAO = new SQLAuthTokenDAO();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            Headers reqHeaders = exchange.getRequestHeaders();
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

            if (!reqHeaders.containsKey("authToken")) {
                send400(exchange, "You are already logged out");
                return;
            }

            String token = reqHeaders.getFirst("authToken");
            authTokenDAO.removeToken(token);
            send200(exchange);

        }
        catch (IOException e) {
            send500(exchange);
        }

    }

    // handles 200 responses
    private void send200(HttpExchange exchange) throws IOException {
        SignOutResponse resp = new SignOutResponse();
        resp.message = "Logout successful";

        exchange.sendResponseHeaders(200, 0);
        OutputStream respBody = exchange.getResponseBody();
        HttpUtils.writeString(g.toJson(resp), respBody);
        respBody.close();
    }

    // handles 400 responses with custom message
    private void send400(HttpExchange exchange, String message) throws IOException {
        SignOutResponse resp = new SignOutResponse();
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