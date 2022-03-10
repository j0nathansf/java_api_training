package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.HttpURLConnection;

public class StartHandler implements HttpHandler {

    private final JSONObject response = new JSONObject("{\n" +
        "  \"id\": \"string\",\n" +
        "  \"url\": \"string\",\n" +
        "  \"message\": \"string\",\n" +
        "}");

    public boolean verifyBody(HttpExchange exchange) {
        try {
            File schemaFile = new File("src/main/resources/startSchema.json");
            JSONTokener schemaData = new JSONTokener(new FileInputStream(schemaFile));
            JSONObject jsonSchema = new JSONObject(schemaData);
            JSONTokener schemaDataFile = new JSONTokener(new InputStreamReader(exchange.getRequestBody()));
            JSONObject jsonData = new JSONObject(schemaDataFile);
            Schema schemaValidator = SchemaLoader.load(jsonSchema);
            schemaValidator.validate(jsonData);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public void setResponse(int code, String url) {
        response.put("id", "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d");
        response.put("url", url);
        switch(code) {
            case 1:
                response.put("message", "OK!");
                break;
            case 2:
                response.put("message", "Bad request !");
                break;
            default:
                response.put("message", "Not found !");
                break;
        }
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String url = "http://localhost:" + exchange.getLocalAddress().getPort() + exchange.getRequestURI().toString();
        if ("POST".contentEquals(exchange.getRequestMethod())) {
            if (verifyBody(exchange)) {
                setResponse(1, url);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_ACCEPTED, response.toString().length());
                exchange.getResponseBody().write(response.toString().getBytes());
            } else {
                setResponse(2, url);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_ACCEPTED, response.length());
                exchange.getResponseBody().write(response.toString().getBytes());
            }
        } else {
            setResponse(3, url);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, "Not found !".length());
            exchange.getResponseBody().write("Not found !".getBytes());
        }
        exchange.close();
    }
}
