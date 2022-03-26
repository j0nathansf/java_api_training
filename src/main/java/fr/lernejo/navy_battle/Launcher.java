package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;
import org.apache.commons.validator.routines.UrlValidator;

public class Launcher {


    public static Server initServer(int port, String url) {
        if (url.length() == 0) return new Server(port);
        UrlValidator urlValidator = new UrlValidator(UrlValidator.ALLOW_LOCAL_URLS);
        if (urlValidator.isValid(url)) {
            return new Server(port, url);
        } else {
            return new Server(port);
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) System.out.println("No port given !");
        else {
            try {
                int port = Integer.parseInt(args[0]);
                Server s = initServer(port, (args.length >= 2) ? args[1] : "");
                HttpServer server = s.initServer();
                if (args.length == 2) s.createClient(args[1]);
                server.start();
                System.out.println("Server is listening on port " + port);
            }
            catch (Exception e) { System.err.println(e); }
        }
    }
}
