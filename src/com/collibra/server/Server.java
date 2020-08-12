package com.collibra.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Simple TCP/IP socket server implementation.
 */
public class Server {

    private static final int PORT_NUMBER = 50000;
    private static final int SO_TIMEOUT_MS = 30000;

    public static void main(String[] args) throws IOException {
        try (ServerSocket listener = new ServerSocket(PORT_NUMBER)) {
            System.out.println("The server is running...");
            Socket socket = null;
            while (true) {
                socket = listener.accept();
                System.out.println("Accepted a connection");
                socket.setSoTimeout(SO_TIMEOUT_MS);
                new ServerThread(socket).start();
            }
        }
    }
}




