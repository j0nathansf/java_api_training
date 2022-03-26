package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;
import org.apache.commons.validator.routines.UrlValidator;

public class Launcher {

    public static void main(String[] args) {
        if (args.length < 1) System.out.println("No port given !");
        else {
            try {
                int port = Integer.parseInt(args[0]);
                Server s = new Server(port);
                HttpServer server = s.initServer();
                UrlValidator urlValidator = new UrlValidator(UrlValidator.ALLOW_LOCAL_URLS);
                if (args.length == 2 && urlValidator.isValid(args[1])) s.createClient(args[1]);
                server.start();
                System.out.println("Server is listening on port " + port);
            }
            catch (Exception e) { System.err.println(e); }
        }
    }
}
