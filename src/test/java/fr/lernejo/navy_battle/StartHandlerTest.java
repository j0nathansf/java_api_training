package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class StartHandlerTest {

    @Test
    void test_start_handler_good_params() throws IOException, InterruptedException {
        Server s = new Server(5000);
        HttpServer server = s.initServer();
        server.start();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:5000/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{ \"id\": \"0c575465-21f6-43c9-8a2d-bc64c3ae6241\", \"url\": \"http://localhost:8795\", \"message\": \"I will crush you!\" }"))
            .build();
        HttpResponse<String> resp = client.send(request, HttpResponse.BodyHandlers.ofString());
        server.stop(1);
        int expected = HttpURLConnection.HTTP_ACCEPTED;
        int result = resp.statusCode();
        Assertions.assertThat(result).as("Response should be 200 OK").isEqualTo(expected);
    }

    @Test
    void test_start_handler_bad_params() throws IOException, InterruptedException {
        Server s = new Server(5000);
        HttpServer server = s.initServer();
        server.start();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:5000/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{ \"id\": \"0c575465-21f6-43c9-8a2d-bc64c3ae6241\", \"url\": \"http://localhost:8795\""))
            .build();
        HttpResponse<String> resp = client.send(request, HttpResponse.BodyHandlers.ofString());
        server.stop(1);
        int expected = HttpURLConnection.HTTP_BAD_REQUEST;
        int result = resp.statusCode();
        Assertions.assertThat(result).as("Response should be 400 Bad Request").isEqualTo(expected);
    }

    @Test
    void test_start_handler_bad_method() throws IOException, InterruptedException {
        Server s = new Server(5000);
        HttpServer server = s.initServer();
        server.start();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:5000/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .GET()
            .build();
        HttpResponse<String> resp = client.send(request, HttpResponse.BodyHandlers.ofString());
        server.stop(1);
        int expected = HttpURLConnection.HTTP_NOT_FOUND;
        int result = resp.statusCode();
        Assertions.assertThat(result).as("Response should be 404 Not Found").isEqualTo(expected);
    }
}
