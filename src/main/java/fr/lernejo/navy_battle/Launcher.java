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
                InetSocketAddress address = new InetSocketAddress(port);
                HttpServer server = HttpServer.create(address, 0);
                server.setExecutor(Executors.newFixedThreadPool(1));
                server.createContext("/ping", new PingHandler());
                server.createContext("/api/game/start", new StartHandler());
                server.start();
                System.out.println("Server is listening on port " + port);
            }
            catch (NumberFormatException e) {
                System.err.println(e);
            }
        }
    }
}
