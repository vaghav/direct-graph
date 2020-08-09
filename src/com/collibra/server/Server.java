package com.collibra.server;


import com.collibra.graph.Graph;
import com.collibra.graph.GraphImpl;
import com.collibra.graph.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Simple server TCP socket server implementation.
 */
public class Server {

    private static final int PORT_NUMBER = 50000;
    private static final int SO_TIMEOUT_MS = 30000;
    private static volatile Graph graph = new GraphImpl();

    public static void main(String[] args) throws IOException {
        try (ServerSocket listener = new ServerSocket(PORT_NUMBER)) {
            System.out.println("The server is running...");
            while (true) {
                try (Socket socket = listener.accept()) {
                    socket.setSoTimeout(SO_TIMEOUT_MS);
                    handleClientRequests(socket);
                }
            }
        }

    }

    private static void handleClientRequests(Socket socket) throws IOException {
        long startTime = System.nanoTime();
        sendMessage(socket, "HI, I AM " + UUID.randomUUID());
        //TODO (vagharshak): Replace ```split(..)``` with Pattern matcher.
        String clientName = readReceivedMessage(socket).split(" ")[3];
        sendMessage(socket, "HI " + clientName);
        String receivedMessage = readReceivedMessage(socket);

        if (receivedMessage != null && !receivedMessage.isEmpty()) {
            Pattern pattern = Pattern.compile("^ADD NODE ([A-Za-z0-9\\-]+)$");
            Matcher matcher = pattern.matcher(receivedMessage);
            boolean isAddNodeCommand = matcher.find();

            if (!receivedMessage.matches("HI, I AM .*") &&
                    !"BYE MATE!".equals(receivedMessage) && !isAddNodeCommand) {
                sendMessage(socket, "SORRY, I DID NOT UNDERSTAND THAT");
            }

            if (isAddNodeCommand) {
                addNodeToGraph(matcher);
                sendMessage(socket, "NODE ADDED");
            }

            if ("BYE MATE!".equals(readReceivedMessage(socket))) {
                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 1000000;
                sendMessage(socket, String.format("BYE %s, WE SPOKE FOR %d MS", clientName, duration));
            }
        } else {
            throw new IllegalStateException("Empty message received from client");
        }
    }

    private static synchronized void addNodeToGraph(Matcher matcher) {
        graph.addNode(new Node(matcher.group(1)));
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
