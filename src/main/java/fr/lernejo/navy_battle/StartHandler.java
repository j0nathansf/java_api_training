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


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".contentEquals(exchange.getRequestMethod())) {
            if (verifyBody(exchange)) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_ACCEPTED, 3);
                exchange.getResponseBody().write("OK!".getBytes());
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
