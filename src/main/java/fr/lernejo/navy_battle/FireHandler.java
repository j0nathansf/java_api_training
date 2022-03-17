package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FireHandler implements HttpHandler {

    private final JSONObject response = new JSONObject("{\n" +
        "  \"consequence\": \"miss\",\n" +
        "  \"shipLeft\": \"true\",\n" +
        "}");

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
            Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery());
            if (!Objects.isNull(params) && !Objects.isNull(params.get("cell")) && !params.get("cell").equals("")) {
                exchange.getResponseHeaders().add("Content-Type", "application/json");
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.toString().length());
                exchange.getResponseBody().write(response.toString().getBytes());
            } else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, "Bad request !".length());
                exchange.getResponseBody().write("Bad request !".getBytes());
            }
        } else {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, "Not found !".length());
            exchange.getResponseBody().write("Not found !".getBytes());
        }
        exchange.close();
    }
}
