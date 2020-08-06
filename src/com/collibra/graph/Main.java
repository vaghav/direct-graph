package com.collibra.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

public class Main {

    private static final int PORT_NUMBER = 50000;
    public static final int SO_TIMEOUT_MS = 30000;

    public static void main(String[] args) throws IOException {
        try (ServerSocket listener = new ServerSocket(PORT_NUMBER)) {
            System.out.println("The server is running...");
            while (true) {
                try (Socket socket = listener.accept()) {
                    socket.setSoTimeout(SO_TIMEOUT_MS);
                    long startTime = System.nanoTime();
                    sendMessage(socket, "HI, I AM " + UUID.randomUUID());
                    //TODO (vagharshak): Replace ```split(..)``` with Pattern matcher.
                    String clientName = readReceivedMessage(socket).split(" ")[3];
                    sendMessage(socket, "HI " + clientName);
                    String receivedMessage = readReceivedMessage(socket);
                    if (!receivedMessage.matches("HI, I AM .*") ||
                            !"BYE MATE!".equals(receivedMessage)) {
                        sendMessage(socket, "SORRY, I DID NOT UNDERSTAND THAT");
                    }
                    if ("BYE MATE!".equals(readReceivedMessage(socket))) {
                        long  endTime= System.nanoTime();
                        long duration = (endTime - startTime) / 1000000;
                        sendMessage(socket, String.format("BYE %s, WE SPOKE FOR %d MS", clientName, duration));
                    }
                }
            }
        }

    }

    private static String readReceivedMessage(Socket socket) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        return input.readLine();
    }

    private static void sendMessage(Socket socket, String message) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(message);
    }
}
