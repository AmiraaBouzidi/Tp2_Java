
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class TCPServerTest {
    private TCPServer tcpServer;
    private Thread serverThread;

    @BeforeEach
    public void setUp() {
        tcpServer = new TCPServer(9998);
        serverThread = new Thread(tcpServer::launch);
        serverThread.start();
    }

    @AfterEach
    public void tearDown() {
        tcpServer.closeServerSocket();
        serverThread.interrupt();
    }

    @Test
    public void testServerHandlesConnection() {
        assertDoesNotThrow(() -> {
            Socket clientSocket = new Socket("localhost", 9998);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            out.println("Hello Server");
            String response = in.readLine();
            System.out.println("Response: " + response);

            clientSocket.close();
        });
    }
}
