package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PingHandlerTest {

    private HttpResponse<String> resp;

    @BeforeEach
    void setUp() throws IOException, InterruptedException {
        Server s = new Server(5000);
        HttpServer server = s.initServer();
        server.start();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:5000/ping"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .GET()
            .build();
        resp = client.send(request, HttpResponse.BodyHandlers.ofString());
        server.stop(1);
    }

    @Test
    void test_ping_route_status_code() {
        int expected = HttpURLConnection.HTTP_OK;
        int result = resp.statusCode();
        Assertions.assertThat(result).as("Response should be 200 OK").isEqualTo(expected);
    }
}
