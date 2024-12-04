package fr.ensea.TPJava;

import java.io.Console;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {
    private String serverAddress;
    private int serverPort;
    private DatagramSocket socket;

    /**
     * Constructor to initialize the server address and port.
     *
     * @param serverAddress the address of the server
     * @param serverPort the port of the server
     */
    public UDPClient(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    /**
     * Initializes the UDP socket.
     *
     * @throws Exception if the socket fails to initialize
     */
    public void initialize() throws Exception {
        if (socket == null || socket.isClosed()) {
            socket = new DatagramSocket();
            System.out.println("UDPClient initialized and ready to send messages.");
        }
    }

   
    public void sendMessage(String message) throws Exception {
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty.");
        }

        byte[] buffer = message.getBytes("UTF-8");
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
                InetAddress.getByName(serverAddress), serverPort);
        socket.send(packet);
        System.out.println("Message sent: " + message);
    }

    
    public void close() {
        if (socket != null && !socket.isClosed()) {
            socket.close();
            System.out.println("UDPClient socket closed.");
        }
    }

   
    public void interactiveSession() {
        try {
            initialize();

            Console console = System.console();
            if (console == null) {
                System.out.println("No console available!");
                return;
            }

            String message;
            while (true) {
                message = console.readLine("Enter a message (or 'exit' to quit): ");
                if (message.equalsIgnoreCase("exit")) {
                    break;
                }

                sendMessage(message); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(); 
        }
    }

    /**
     * Main method to start the UDP client with interactive mode.
     *
     * @param args command-line arguments: server address and port
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java UDPClient <server_address> <port>");
            return;
        }

        String serverAddress = args[0];
        int serverPort = Integer.parseInt(args[1]);

        UDPClient client = new UDPClient(serverAddress, serverPort);
        client.interactiveSession();
    }
}
