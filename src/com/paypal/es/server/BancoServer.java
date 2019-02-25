package com.paypal.es.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BancoServer {
	public static final int PORT = 8080;

    public static void main(String[] args)
            throws IOException {
        ServerSocket s = new ServerSocket(PORT);
        System.out.println("Server started");
            while (true) {
            // Blocks until connection happens:
                Socket socket = s.accept();//SE CREA EL SOCKET DEL CLIENT
                try {
                    new ClientHandler(socket);//Toma el objeto socket cada vez que un nuevo cliente establece una conexión
                } catch (IOException e) {
                    // If it fails, close the socket
                    socket.close();
                }

            }       
    }
}
