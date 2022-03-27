package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FireHandler implements HttpHandler {

    private final Game game;
    private final HashMap<String, Boolean> running;
    private final JSONObject response = new JSONObject("{\n" +
        "  \"consequence\": \"miss\",\n" +
        "  \"shipLeft\": \"true\",\n" +
        "}");

    public FireHandler(Game game) {
        this.game = game;
        this.running = new HashMap<>();
        this.running.put("running", true);
    }

    public Map<String, String> queryToMap(String query) {
        if(query == null) {
            return null;
        }
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            } else {
                result.put(entry[0], "");
            }
        }
        return result;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".contentEquals(exchange.getRequestMethod())) {
            System.out.println("Called");
            Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery());
            if (!Objects.isNull(params) && !Objects.isNull(params.get("cell")) && !params.get("cell").equals("")) {
                sendResponse(exchange, response.toString(), HttpURLConnection.HTTP_OK);
                try { if (this.running.get("running") && this.game.getUrl().length() != 0) this.sendFire("http://localhost:" + exchange.getRemoteAddress().getPort(), "F1"); }
                catch (InterruptedException e) { e.printStackTrace(); }
            } else {
                sendResponse(exchange, "Bad request !", HttpURLConnection.HTTP_BAD_REQUEST);
            }
        } else {
            sendResponse(exchange, "Not found !", HttpURLConnection.HTTP_NOT_FOUND);
        }
        exchange.close();
    }

    public void sendResponse(HttpExchange exchange, String response, int code) throws IOException {
        if (code == HttpURLConnection.HTTP_OK) exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(code, response.length());
        exchange.getResponseBody().write(response.getBytes());
    }

    public String sendFire(String adversaryURL, String cell) throws IOException, InterruptedException {
        if (this.running.get("running")) { this.running.put("running", false); }
        HttpClient client = this.game.getClient();
        HttpRequest fireRequest = HttpRequest.newBuilder()
            .uri(URI.create(adversaryURL + "/api/game/fire?cell=" + cell))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .build();
        HttpResponse<String> response = client.send(fireRequest, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
