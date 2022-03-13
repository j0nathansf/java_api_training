package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Launcher {

    public static void main(String[] args) throws IOException {
        if (args.length < 1) System.out.println("No port given !");
        else {
            try {
                int port = Integer.parseInt(args[0]);
                HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
                server.setExecutor(Executors.newFixedThreadPool(1));
                server.createContext("/ping", new PingHandler());
                StartHandler start = new StartHandler();
                server.createContext("/api/game/start", start);
                if (args.length == 2) start.createClient(args[1], port);
                server.createContext("/api/game/fire", new FireHandler());
                server.start();
                System.out.println("Server is listening on port " + port);
            }
            catch (Exception e) { System.err.println(e); }
        }
    }
}
