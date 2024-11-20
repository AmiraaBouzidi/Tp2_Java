package fr.ensea.TPJava;
import java.io.Console;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
/**
 * Classe représentant un client UDP qui envoie des messages à un serveur.
 */
public class UDPClient {
    private String serverAddress;
    private int serverPort;

    /**
     * Constructeur pour initialiser l'adresse et le port du serveur.
     *
     * @param serverAddress l'adresse du serveur
     * @param serverPort le port du serveur
     */
    public UDPClient(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    // Méthode pour envoyer des messages
    public void sendMessages() {
        try {
            DatagramSocket socket = new DatagramSocket();


            Console console = System.console();
            if (console == null) {
                System.out.println("Aucune console disponible !");
                return;
            }

            String message;
            while (true) {

                message = console.readLine("Entrez un message (ou 'exit' pour quitter) : ");
                if (message.equalsIgnoreCase("exit")) {
                    break;
                }

                // Encoder le message en UTF-8
                byte[] buffer = message.getBytes("UTF-8");


                DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
                        InetAddress.getByName(serverAddress), serverPort);

                // Envoyer le paquet
                socket.send(packet);
                System.out.println("Message envoyé : " + message);
            }

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Méthode principale pour démarrer le client UDP.
     *
     * @param args arguments de la ligne de commande, adresse du serveur et port
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java UDPClient <adresse_serveur> <port>");
            return;
        }

        String serverAddress = args[0];
        int serverPort = Integer.parseInt(args[1]);

        UDPClient client = new UDPClient(serverAddress, serverPort);
        client.sendMessages();
    }
}
