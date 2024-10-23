package fr.ensea.TPJava;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {
    private int port;
    private DatagramSocket socket;

    /**
     * Constructeur avec argument spécifiant le port d'écoute.
     *
     * @param port le port sur lequel le serveur doit écouter
     */
    public UDPServer(int port) {

        this.port = port;
    }

    // Constructeur par défaut
    public UDPServer() {

        this(8080);
    }

    // Démarrage du serveur
    public void launch() {
        try {
            socket = new DatagramSocket(port);
            System.out.println("Server listening on port " + port);

            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String message = new String(packet.getData(), 0, packet.getLength(), "UTF-8");
                if (message.length() > 1024) {
                    message = message.substring(0, 1024);
                }

                InetAddress clientAddress = packet.getAddress();
                int clientPort = packet.getPort();
                System.out.printf("Received from %s:%d - %s%n", clientAddress, clientPort, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {

        return "UDPServer listening on port " + port;
    }

    /**
     * Méthode principale pour démarrer le serveur UDP.
     *
     * @param args arguments de la ligne de commande, port d'écoute optionnel
     */
    public static void main(String[] args) {
        int port = (args.length > 0) ? Integer.parseInt(args[0]) : 8080;
        UDPServer server = new UDPServer(port);
        server.launch();
    }
}
