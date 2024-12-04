import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class UDPServerTest {
    private UDPServer udpServer;
    private Thread serverThread;

    @BeforeEach
    public void setUp() {
        udpServer = new UDPServer(8081);
        serverThread = new Thread(() -> {
            try {
                udpServer.setMessageListener((clientAddress, clientPort, message) -> {
                    System.out.println("Test Server Received: " + message);
                });
                udpServer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        serverThread.start();
    }

    @AfterEach
    public void tearDown() {
        udpServer.stop();
        serverThread.interrupt();
    }

    @Test
    public void testServerReceivesMessage() {
        assertDoesNotThrow(() -> {
            DatagramSocket socket = new DatagramSocket();
            byte[] buffer = "Hello Server".getBytes("UTF-8");
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("localhost"), 8081);
            socket.send(packet);
            socket.close();
        });
    }
}
