package fr.lernejo.navy_battle;

import java.net.http.HttpClient;
import java.util.HashMap;

public class Game {
    private final HashMap<String, String> url;
    private final HashMap<String, HttpClient> client;


    public Game(HashMap<String, String> url, HashMap<String, HttpClient> client) {
        this.url = url;
        this.client = client;
    }

    public void setClient(HttpClient newClient) {
        this.client.put("client", newClient);
    }

    public void setUrl(String newUrl) {
        this.url.put("url", newUrl);
    }

    public HttpClient getClient() {
        return this.client.get("client");
    }

    public String getUrl() {
        return this.url.get("url");
    }
}
