import com.sun.net.httpserver.HttpServer;
import config.RouteConfig;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        RouteConfig.register(server);

        server.setExecutor(null);
        server.start();

        System.out.println("Server running on http://localhost:8080");
    }

}
