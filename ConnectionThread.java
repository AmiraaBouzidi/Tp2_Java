package fr.ensea.TPJava;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles a single client connection in a separate thread.
 */
public class ConnectionThread extends Thread {
    private static final Logger LOGGER = Logger.getLogger(ConnectionThread.class.getName());

    private final Socket socket;

    /**
     * Constructor to initialize the client socket.
     *
     * @param socket the client socket
     */
    public ConnectionThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        LOGGER.log(Level.INFO, "Connection thread started for client: {0}:{1}",
                new Object[]{socket.getInetAddress().getHostAddress(), socket.getPort()});

        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                LOGGER.log(Level.INFO, "Received from {0}:{1} - {2}",
                        new Object[]{socket.getInetAddress().getHostAddress(), socket.getPort(), inputLine});

                out.println("Server Echo: " + inputLine);
            }

        } catch (Exception e) {
            LOGGER.log(Level.WARNING,
                    String.format("Error while handling client: %s:%d",
                            socket.getInetAddress().getHostAddress(),
                            socket.getPort()),
                    e);

        } finally {
            closeSocket();
        }
    }

 
    private void closeSocket() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
                LOGGER.log(Level.INFO, "Connection closed for client: {0}:{1}",
                        new Object[]{socket.getInetAddress().getHostAddress(), socket.getPort()});
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while closing client socket", e);
        }
    }
}
