package config;

import com.sun.net.httpserver.HttpServer;
import handler.AccountHandler;

public class RouteConfig {

    public static void register(HttpServer server) {
        server.createContext("/accounts", new AccountHandler());
    }

}
