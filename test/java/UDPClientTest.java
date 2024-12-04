import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class UDPClientTest {

    @Test
    public void testSendMessage() {
        assertDoesNotThrow(() -> {
            UDPClient client = new UDPClient("localhost", 8081);
            client.initialize();
            client.sendMessage("Hello Server");
            client.close();
        });
    }
}
