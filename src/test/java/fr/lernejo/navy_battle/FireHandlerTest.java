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

class FireHandlerTest {
    @Test
    void test_fire_handler_good_params() throws IOException, InterruptedException {
        Server s = new Server(5000);
        HttpServer server = s.initServer();
        server.start();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:5000/api/game/fire?cell=F2"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .GET()
            .build();
        HttpResponse<String> resp = client.send(request, HttpResponse.BodyHandlers.ofString());
        server.stop(1);
        int expected = HttpURLConnection.HTTP_OK;
        int result = resp.statusCode();
        Assertions.assertThat(result).as("Response should be 200 OK").isEqualTo(expected);
    }

    @Test
    void test_fire_handler_bad_params() throws IOException, InterruptedException {
        Server s = new Server(5000);
        HttpServer server = s.initServer();
        server.start();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:5000/api/game/fire"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .GET()
            .build();
        HttpResponse<String> resp = client.send(request, HttpResponse.BodyHandlers.ofString());
        server.stop(1);
        int expected = HttpURLConnection.HTTP_BAD_REQUEST;
        int result = resp.statusCode();
        Assertions.assertThat(result).as("Response should be 400 Bad Request").isEqualTo(expected);
    }

    @Test
    void test_fire_handler_bad_method() throws IOException, InterruptedException {
        Server s = new Server(5000);
        HttpServer server = s.initServer();
        server.start();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:5000/api/game/fire?cell=F2"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .DELETE()
            .build();
        HttpResponse<String> resp = client.send(request, HttpResponse.BodyHandlers.ofString());
        server.stop(1);
        int expected = HttpURLConnection.HTTP_NOT_FOUND;
        int result = resp.statusCode();
        Assertions.assertThat(result).as("Response should be 404 Not Found").isEqualTo(expected);
    }
}
