package fr.lernejo.navy_battle;

import java.util.HashMap;

public class Game {
    private final HashMap<String, String> url;


    public Game(HashMap<String, String> url) {
        this.url = url;
    }

    public void setUrl(String newUrl) {
        this.url.put("url", newUrl);
    }

    public String getUrl() {
        return this.url.get("url");
    }
}
