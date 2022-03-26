package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertTrue;


class LauncherTest {

    @Test
    public void test_no_args() {
        Launcher.main(new String[] {""});
    }

    @Test
    public void test_bad_port() {
        try {
            Launcher.main(new String[] {"Port"});
        } catch (Throwable e) {
            assertTrue(e instanceof RuntimeException);
        }
    }

    @Test void test_client_server() throws IOException, InterruptedException {
        Server s = new Server(5000);
        HttpServer server = s.initServer();
        server.start();
        Server s1 = new Server(5001);
        HttpServer server1 = s1.initServer();
        server1.start();
        HttpResponse<String> resp = s1.createClient("http://localhost:5000");
        int expected = HttpURLConnection.HTTP_ACCEPTED;
        int result = resp.statusCode();
        server.stop(1);
        server1.stop(1);
        Assertions.assertThat(result).as("Response should be 202 Accepted").isEqualTo(expected);
    }
}
