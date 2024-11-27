package fr.ensea.TPJava;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TCPServer {
    private static final Logger LOGGER = Logger.getLogger(TCPServer.class.getName());

    private int port;
    private ServerSocket serverSocket;

    /**
     * Constructor specifying the listening port.
     *
     * @param port the port on which the server will listen
     */
    public TCPServer(int port) {
        this.port = port;
    }


    public TCPServer() {
        this(8081);
    }


    public void launch() {
        try {
            serverSocket = new ServerSocket(port);
            LOGGER.log(Level.INFO, "Server listening on port {0}", port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                LOGGER.log(Level.INFO, "Connection accepted from {0}:{1}",
                        new Object[]{clientSocket.getInetAddress().getHostAddress(), clientSocket.getPort()});

               
                handleClient(clientSocket);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error occurred in server operation", e);
        } finally {
            closeServerSocket();
        }
    }

    /**
     * Handles communication with a connected client.
     *
     * @param clientSocket the socket representing the client connection
     */
    private void handleClient(Socket clientSocket) {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                LOGGER.log(Level.INFO, "Received from {0}: {1}",
                        new Object[]{clientSocket.getInetAddress().getHostAddress(), inputLine});


                out.println(clientSocket.getInetAddress().getHostAddress() + ": " + inputLine);
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error while handling client connection", e);
        } finally {
            try {
                clientSocket.close();
                LOGGER.log(Level.INFO, "Connection closed with client {0}:{1}",
                        new Object[]{clientSocket.getInetAddress().getHostAddress(), clientSocket.getPort()});
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error while closing client socket", e);
            }
        }
    }

    private void closeServerSocket() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                LOGGER.log(Level.INFO, "Server socket closed.");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while closing server socket", e);
        }
    }

    @Override
    public String toString() {
        return "TCPServer listening on port " + port;
    }

    /**
     * Main method to start the TCP server.
     * If a port number is provided as a command-line argument, the server will
     * listen on that port; otherwise, it defaults to port 8080.
     *
     * @param args command-line arguments, optional listening port
     */
    public static void main(String[] args) {
        int port = (args.length > 0) ? Integer.parseInt(args[0]) : 8081;
        TCPServer server = new TCPServer(port);
        server.launch();
    }
}
