    1. Creating a UDP Client-Server
   
		  1.1 UDP Server

   In this section, we want to create a UDPServer class representing a server that expects
to receive datagrams from its clients containing strings encoded in ”UTF-8”.
o test the server, we use netcat in another terminal with the following command :
echo "Hello UDP Server" | nc -u localhost 8080


![image](https://github.com/user-attachments/assets/c98bbbcb-3541-4bf4-9283-962e963efa5a)


		1.2 UDP Client
In a second step, we want to create a UDPClient class that reads the text lines entered by
the user from the standard input and sends them, encoded in ”utf-8”, in a UDP datagram
to the server specified in the argument on the command line.

![image](https://github.com/user-attachments/assets/634c260f-2cab-4a57-8b92-2425ffcfc33a)

This shows a successful implementation of a UDP communication between a client and
server in Java. The user compiled and ran the UDPClient and successfully connected to
the server on localhost at port 8080.
After entering the message, the client sent it to the server, which received and displayed it
along with the client’s IP address and port. This confirms that the UDP chat application is
functioning correctly, with proper message transmission and reception between the client
and server.

	2. Creating a TCP Client-Server

In this part, a communication between a client process and a server process is established
over TCP. 

		2.1 TCP Server

This implementation of the TCPServer class follows a simple client-server communication
model over TCP. The server listens on a specified port, accepts incoming client connections, and reads the text messages sent by clients. After receiving a message, the server
echoes it back to the client, prefixed by the client’s IP address. The server runs in a continuous loop, allowing multiple clients to connect one after another. This approach ensures
that the server handles basic TCP communication efficiently, while keeping the code easy
to understand and extend.


![image](https://github.com/user-attachments/assets/59235aa7-ce4c-43ef-bb9b-c4797eb5f077)

The TCPServer is actively running and handling connections on port 8080. The terminal
shows a successful interaction between the client and the server using the nc (netcat)
tool to connect to localhost on port 8080. The server output shows it correctly accepting
the connection and processing the messages. This confirms that the TCP echo server is
functioning as intended, reliably handling and responding to client requests.

    2.2 TCP Client

Now, We will create a TCPClient class on the model of UDPClient, which establishes a TCP connection
with the server specified on the command line (address, port). Once the connection is established,
this client reads a line of text from the standard input by the user, sends it to the server
after encoding it in UTF8, and reads the hexa text line returned by the server as a response.

![Capture d'écran 2024-11-13 143614](https://github.com/user-attachments/assets/1253a136-a5e6-481c-b611-6c0908009a03)


    2.3 Server accepting multiple TCP connections
    
To create a server capable of accepting multiple simultaneous TCP connections in Java, you can follow these steps using threads to handle each client concurrently.

* Create a TCPMultiServer class: This class listens for incoming connections and creates a new thread for each client that connects.

* Handle multiple clients with threads: Use a ConnectionThread class for each client. This class will inherit from Thread and contain a loop in the run() method to read and respond to messages from the client.

![Capture d'écran 2024-11-13 150104](https://github.com/user-attachments/assets/32833ad2-f9a6-4e20-920a-6d77dcbf4d29)

  TCPMultiServer is working correctly, accepting multiple TCP connections and responding to each message with an echo. We used Telnet to test the functionality by sending multiple messages from a client, and the server responded appropriately with the prefix Echo du serveur.
