package fr.ensea.TPJava;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


public class TCPClient {

    /**
     * Méthode principale qui démarre le client TCP.
     *
     * @param args les arguments de la ligne de commande, où args[0] est l'adresse du serveur
     *             et args[1] est le port du serveur.
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java TCPClient <address> <port>");
            return;
        }

        String serverAddress = args[0];
        int port = Integer.parseInt(args[1]);

        try (

                Socket socket = new Socket(serverAddress, port);

                BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
                BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                PrintWriter serverOutput = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true)
        ) {
            System.out.println("Connected to the server at " + serverAddress + ":" + port);
            String userLine;

            while ((userLine = userInput.readLine()) != null) {
                serverOutput.println(userLine);
                String serverResponse = serverInput.readLine();
                if (serverResponse != null) {
                    System.out.println("Response from server: " + serverResponse);
                } else {
                    System.out.println("Server closed the connection.");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
