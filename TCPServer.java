package fr.ensea.TPJava;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private int port;
    private ServerSocket serverSocket;

    /**
     * Constructeur avec argument spécifiant le port d'écoute.
     *
     * @param port le port sur lequel le serveur doit écouter
     */
    public TCPServer(int port) {
        this.port = port;
    }

    // Constructeur par défaut (port 8080)
    public TCPServer() {
        this(8080);
    }

    // Démarrage du serveur
    public void launch() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port " + port);

            while (true) {
                // Attendre une connexion
                Socket clientSocket = serverSocket.accept();
                System.out.printf("Connection accepted from %s:%d%n",
                        clientSocket.getInetAddress().getHostAddress(),
                        clientSocket.getPort());


                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                // Lire les messages du client
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.printf("Received from %s: %s%n",
                            clientSocket.getInetAddress().getHostAddress(),
                            inputLine);

                    out.println(clientSocket.getInetAddress().getHostAddress() + ": " + inputLine);
                }

                clientSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "TCPServer listening on port " + port;
    }
    /**
     * Méthode principale pour démarrer le serveur TCP.
     *
     * @param args arguments de la ligne de commande, port d'écoute optionnel
     */
    public static void main(String[] args) {
        int port = (args.length > 0) ? Integer.parseInt(args[0]) : 8080;
        TCPServer server = new TCPServer(port);
        server.launch();
    }
}
