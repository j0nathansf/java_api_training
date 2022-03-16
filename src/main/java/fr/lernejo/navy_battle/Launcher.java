package fr.lernejo.navy_battle;

public class Launcher {

    public static void main(String[] args) {
        if (args.length < 1) System.out.println("No port given !");
        else {
            try {
                int port = Integer.parseInt(args[0]);
                Server server = new Server(port);
                server.initServer();
                if (args.length == 2) server.createClient(args[1]);
                System.out.println("Server is listening on port " + port);
            }
            catch (Exception e) { System.err.println(e); }
        }
    }
}
