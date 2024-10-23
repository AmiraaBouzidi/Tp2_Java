import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {
    private String serverAddress;
    private int serverPort;


    public TCPClient(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }


    public void start() {
        try (Socket socket = new Socket(serverAddress, serverPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            String userInput;
            while (true) {
                System.out.print("Entrez un message (ou 'exit' pour quitter) : ");
                userInput = scanner.nextLine();
                if ("exit".equalsIgnoreCase(userInput)) {
                    break; // Quitte si l'utilisateur tape 'exit'
                }
                out.println(userInput);
                String response = in.readLine();
                System.out.println("Réponse du serveur : " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java TCPClient <adresse_serveur> <port>");
            return;
        }

        String serverAddress = args[0];
        int serverPort = Integer.parseInt(args[1]);

        TCPClient client = new TCPClient(serverAddress, serverPort);
        client.start();
    }
}
