package fr.ensea.TPJava;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


public class TCPClient {
    private String serverAddress;
    private int port;
    private Socket socket;
    private BufferedReader userInput;
    private BufferedReader serverInput;
    private PrintWriter serverOutput;

    /**
     * Constructor to initialize the server address and port.
     *
     * @param serverAddress the server address
     * @param port the server port
     */
    public TCPClient(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }

    /**
     * Initializes the connection to the server.
     *
     * @throws Exception if the connection fails
     */
    public void connect() throws Exception {
        socket = new Socket(serverAddress, port);
        userInput = new BufferedReader(new InputStreamReader(System.in));
        serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        serverOutput = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

        System.out.println("Connected to the server at " + serverAddress + ":" + port);
    }


    public void startCommunication() {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

   
    public void close() {
        try {
            if (serverOutput != null) {
                serverOutput.close();
            }
            if (serverInput != null) {
                serverInput.close();
            }
            if (userInput != null) {
                userInput.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            System.out.println("Disconnected from the server.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Main method to start the TCP client.
     *
     * @param args command-line arguments: server address and port
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java TCPClient <address> <port>");
            return;
        }

        String serverAddress = args[0];
        int port = Integer.parseInt(args[1]);

        TCPClient client = new TCPClient(serverAddress, port);
        try {
            client.connect();
            client.startCommunication();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
