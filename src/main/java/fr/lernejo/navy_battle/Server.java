package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.Executors;

public class Server {
    private final Game game;
    private final int port;

    public Server(int port) {
        this.port = port;
        this.game = new Game(new HashMap<>());
    }

    public HttpServer initServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(this.port), 0);
        server.setExecutor(Executors.newFixedThreadPool(1));
        server.createContext("/ping", new PingHandler());
        server.createContext("/api/game/start", new StartHandler(this.game));
        server.createContext("/api/game/fire", new FireHandler(this.game));
        return server;
    }

    public HttpResponse<String> createClient(String adversaryUrl) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        this.game.setUrl(adversaryUrl);
        HttpRequest requestPost = HttpRequest.newBuilder()
            .uri(URI.create(adversaryUrl + "/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"" + UUID.randomUUID() + "\", \"url\":\"http://localhost:" + this.port + "\", \"message\":\"Start client\"}"))
            .build();
        HttpResponse<String> resp = client.send(requestPost, HttpResponse.BodyHandlers.ofString());
        return resp;
    }

}
