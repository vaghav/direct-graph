package com.collibra.server;

import com.collibra.graph.Graph;
import com.collibra.graph.GraphImpl;
import com.collibra.graph.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerThread extends Thread {

    private static Graph graph = new GraphImpl();

    private Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        long startTime = System.nanoTime();
        String clientName = null;
        PrintWriter outData = null;
        try {
            BufferedReader inData = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outData = new PrintWriter(socket.getOutputStream(), true);
            handshake(outData);
            while (true) {
                Pattern clientNamePattern = Pattern.compile("^HI, I AM ([A-Za-z0-9\\-]+)$");
                String input = readReceivedMessage(inData);
                Matcher clientNameMatcher = clientNamePattern.matcher(input);
                if (clientNameMatcher.find()) {
                    clientName = clientNameMatcher.group(1);
                    sendMessage(outData, "HI " + clientName);
                }

                //TODO: Handle graph processing requests.
                //handleClientRequests(socket);

                String receivedMessage = readReceivedMessage(inData);
                if (!receivedMessage.matches("HI, I AM .*")
                        && !"BYE MATE!".equals(receivedMessage)) {
                    sendMessage(outData, "SORRY, I DID NOT UNDERSTAND THAT");
                }

                String receivedMessage1 = readReceivedMessage(inData);
                if ("BYE MATE!".equals(receivedMessage1)) {
                    long endTime = System.nanoTime();
                    long duration = (endTime - startTime) / 1000000;
                    sendMessage(outData, String.format("BYE %s, WE SPOKE FOR %d MS", clientName, duration));
                }
            }
        } catch (SocketTimeoutException ex) {
            if (outData != null) {
                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 1000000;
                sendMessage(outData, String.format("BYE %s, WE SPOKE FOR %d MS", clientName, duration));
            } else {
                System.out.printf("Socket timeout exception happened: [%s]", ex.getCause());
            }
        } catch (IOException e) {
            System.out.printf("Connection interrupted due to [%s]%n", e.getCause());
        } finally {
            if (outData != null) {
                outData.close();
            }
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Unable close he socket: " + e.getMessage());
            }
        }
    }

    private void handleClientRequests(Socket socket) throws IOException {
        // initialize only once
        BufferedReader inData = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter outData = new PrintWriter(socket.getOutputStream(), true);

        String receivedMessage = readReceivedMessage(inData);

        if (!receivedMessage.isEmpty()) {
            Pattern pattern = Pattern.compile("^ADD NODE ([A-Za-z0-9\\-]+)$");
            Matcher matcher = pattern.matcher(receivedMessage);
            boolean isAddNodeCommand = matcher.find();

            if (isAddNodeCommand) {
                synchronized (this) {
                    graph.addNode(new Node(matcher.group(1)));
                }
                sendMessage(outData, "NODE ADDED");
            }
        } else {
            sendMessage(outData, "SORRY, I DID NOT UNDERSTAND THAT");
        }
    }

    private static void handshake(PrintWriter outData) throws IOException {
        sendMessage(outData, "HI, I AM " + UUID.randomUUID());
    }

    private static String readReceivedMessage(BufferedReader inData) throws IOException {
        String incomingData = inData.readLine();
        if (incomingData == null) {
            // null means that a client closed a socket
            throw new IOException("Socket closed");
        }
        return incomingData;
    }

    private static void sendMessage(PrintWriter outData, String message) {
        System.out.println("Sending message " + message);
        outData.println(message);
    }
}
