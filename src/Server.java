import java.io.*;
import java.net.*;

import Context.GetFeed.GetFeed;
import Context.GetProfile.GetProfile;
import Context.SignIn.SignIn;
import Context.SignOut.SignOut;
import com.sun.net.httpserver.*;

public class Server {

    private static final int MAX_WAITING_CONNECTIONS = 12;
    private HttpServer server;

    private void run(String portNumber) {
        try {
            server = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(portNumber)),
                    MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

        server.setExecutor(null);
        server.createContext("/accounts/signin", new SignIn());
        server.createContext("/accounts/signout", new SignOut());
        server.createContext("/users", new GetProfile());
        server.createContext("/feed", new GetFeed());
        server.start();
        System.out.println("Http server started on " + portNumber);
    }

    public static void main(String[] args) {
        String portNumber = "8181";
        new Server().run(portNumber);
    }
}
