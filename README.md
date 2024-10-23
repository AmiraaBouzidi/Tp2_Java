	1. Creating a UDP Client-Server
   
		1.1 UDP Server

   In this section, we want to create a UDPServer class representing a server that expects
to receive datagrams from its clients containing strings encoded in ”UTF-8”.
o test the server, we use netcat in another terminal with the following command :
echo "Hello UDP Server" | nc -u localhost 8080




		1.2 UDP Client
In a second step, we want to create a UDPClient class that reads the text lines entered by
the user from the standard input and sends them, encoded in ”utf-8”, in a UDP datagram
to the server specified in the argument on the command line.



	2. Creating a TCP Client-Server

In this part, a communication between a client process and a server process is established
over TCP. 

		2.1 TCP Server


