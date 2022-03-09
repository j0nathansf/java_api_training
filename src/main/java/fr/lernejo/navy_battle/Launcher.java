package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Launcher {
    public static void main(String[] args) throws IOException {
        // Get the first parameter
        if (args.length < 1) {
            System.out.println("No port given !");
        } else {
            try {
                // Get the port form args
                int port = Integer.parseInt(args[0]);
                // Start the server at port 8000
                InetSocketAddress address = new InetSocketAddress(port);
                HttpServer server = HttpServer.create(address, 0);
                // Create 1 thread for the server
                server.setExecutor(Executors.newFixedThreadPool(1));
                // Handle API routes
                server.createContext("/ping", new PingHandler());
                // Launch the server and announce to the user the port
                server.start();
                System.out.println("Server is listening on port " + port);
            }
            catch (NumberFormatException e) {
                // Handle bad port
                System.err.println(e);
            }
        }
    }
}
