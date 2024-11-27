package fr.ensea.TPJava;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {
    private int port;
    private DatagramSocket socket;

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
     * Main method to start the UDP server.
     * If a port number is provided in the command-line arguments, the server
     * will listen on that port. Otherwise, it defaults to port 8080.
     *
     * @param args command-line arguments, optional port number
     */
    
    public static void main(String[] args) {
        int port = (args.length > 0) ? Integer.parseInt(args[0]) : 8080;
        UDPServer server = new UDPServer(port);
        server.launch();
    }
}
