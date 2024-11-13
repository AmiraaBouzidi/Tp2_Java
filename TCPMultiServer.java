package fr.ensea.TPJava;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPMultiServer {
    public static void main(String[] args) {
        int port = 8081; // Remplacez 8080 par un port libre
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Serveur démarré, en écoute sur le port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nouveau client connecté : " + clientSocket.getInetAddress().getHostAddress());
                new ConnectionThread(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
