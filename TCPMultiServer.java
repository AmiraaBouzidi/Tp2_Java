package fr.ensea.TPJava;

import org.apache.commons.cli.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


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

    /**
     * Parses command-line arguments and starts the TCP server.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Options options = new Options();

        // Define the "port" option
        Option portOption = Option.builder("p")
                .longOpt("port")
                .hasArg()
                .desc("The port number the server will listen on (default: 8081)")
                .type(Number.class)
                .build();

        // Define the "help" option
        Option helpOption = Option.builder("h")
                .longOpt("help")
                .desc("Display help information")
                .build();

        options.addOption(portOption);
        options.addOption(helpOption);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        int port = 8081; // Default port

        try {
            CommandLine cmd = parser.parse(options, args);

            // Display help if requested
            if (cmd.hasOption("h")) {
                formatter.printHelp("TCPMultiServer", options);
                return;
            }

            // Parse the port if provided
            if (cmd.hasOption("p")) {
                port = ((Number) cmd.getParsedOptionValue("p")).intValue();
            }

        } catch (ParseException e) {
            LOGGER.log(Level.SEVERE, "Failed to parse command-line arguments", e);
            formatter.printHelp("TCPMultiServer", options);
            return;
        }

        // Start the server
        TCPMultiServer server = new TCPMultiServer(port);
        server.start();
    }
}
