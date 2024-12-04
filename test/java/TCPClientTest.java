

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class TCPClientTest {

    @Test
    public void testClientConnectAndSend() {
        assertDoesNotThrow(() -> {
            TCPClient client = new TCPClient("localhost", 9998);
            client.connect();
            client.startCommunication();
            client.close();
        });
    }
}
