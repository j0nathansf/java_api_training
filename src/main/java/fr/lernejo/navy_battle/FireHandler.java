package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;

public class FireHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".contentEquals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, "OK".length());
            exchange.getResponseBody().write("OK".getBytes());
        } else {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, "Not found !".length());
            exchange.getResponseBody().write("Not found !".getBytes());
        }
    }
}
