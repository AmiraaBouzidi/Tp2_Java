package fr.ensea.TPJava;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A multi-threaded TCP server that handles multiple client connections simultaneously.
 */
public class TCPMultiServer {
    private static final Logger LOGGER = Logger.getLogger(TCPMultiServer.class.getName());
    private final int port;

    /**
     * Constructor to initialize the server port.
     *
     * @param port the port on which the server will listen
     */
    public TCPMultiServer(int port) {
        this.port = port;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            LOGGER.log(Level.INFO, "Server started, listening on port {0}", port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                LOGGER.log(Level.INFO, "New client connected: {0}:{1}",
                        new Object[]{clientSocket.getInetAddress().getHostAddress(), clientSocket.getPort()});

                new ConnectionThread(clientSocket).start();
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error occurred in server operation", e);
        }
    }


    public static void main(String[] args) {
        int port = (args.length > 0) ? Integer.parseInt(args[0]) : 8081;
        TCPMultiServer server = new TCPMultiServer(port);
        server.start();
    }
}
