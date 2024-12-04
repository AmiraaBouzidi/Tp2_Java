package fr.ensea.TPJava;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {
    private int port;
    private DatagramSocket socket;
    private boolean running;
    private MessageListener listener;

    /**
     * Constructor with an argument specifying the listening port.
     *
     * @param port the port number on which the server will listen
     */
    public UDPServer(int port) {
        this.port = port;
    }
    public UDPServer() {
        this(8080);
    }

    /**
     * Sets the listener to handle incoming messages.
     *
     * @param listener a custom implementation of MessageListener
     */
    public void setMessageListener(MessageListener listener) {
        this.listener = listener;
    }

    /**
     * Starts the server and listens for incoming datagrams.
     *
     * @throws Exception if the socket cannot be opened
     */
    public void start() throws Exception {
        if (socket != null && !socket.isClosed()) {
            throw new IllegalStateException("Server is already running");
        }

        socket = new DatagramSocket(port);
        running = true;
        System.out.println("Server listening on port " + port);

        byte[] buffer = new byte[1024];

        while (running) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            String message = new String(packet.getData(), 0, packet.getLength(), "UTF-8");
            if (message.length() > 1024) {
                message = message.substring(0, 1024);
            }

            InetAddress clientAddress = packet.getAddress();
            int clientPort = packet.getPort();


            if (listener != null) {
                listener.onMessageReceived(clientAddress, clientPort, message);
            } else {
                System.out.printf("Received from %s:%d - %s%n", clientAddress, clientPort, message);
            }
        }
    }

    /**
     * Stops the server to Make his lifecycle manageable.
     */
    public void stop() {
        running = false;
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
        System.out.println("Server stopped");
    }


    @Override
    public String toString() {
        return "UDPServer listening on port " + port;
    }

    /**
     * Listener interface for handling incoming messages.
     */
    public interface MessageListener {
        void onMessageReceived(InetAddress clientAddress, int clientPort, String message);
    }

    /**
     * Main method to start the server with default behavior.
     *
     * @param args command-line arguments, optional port number
     */
    public static void main(String[] args) {
        try {
            int port = (args.length > 0) ? Integer.parseInt(args[0]) : 8080;
            UDPServer server = new UDPServer(port);

            server.setMessageListener((clientAddress, clientPort, message) -> {
                System.out.printf("Custom handler -> Received from %s:%d - %s%n", clientAddress, clientPort, message);
            });

            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
