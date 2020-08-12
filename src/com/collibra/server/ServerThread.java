package com.collibra.server;

import com.collibra.exceptions.NodeAlreadyExistsException;
import com.collibra.exceptions.NodeNotFoundException;
import com.collibra.graph.Graph;
import com.collibra.graph.GraphImpl;
import com.collibra.graph.Node;
import com.collibra.server.util.ParsedMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.UUID;

import static com.collibra.server.util.MessageParser.parseReceivedMessage;

/**
 * Thread which is responsible to handle client connection and graph processing.
 */
public class ServerThread extends Thread {

    private final Socket socket;
    private final Graph graph = new GraphImpl();

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        long startTime = System.nanoTime();
        java.lang.String clientName = null;
        PrintWriter outData = null;
        try {
            BufferedReader inData = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outData = new PrintWriter(socket.getOutputStream(), true);
            handshake(outData);
            while (true) {
                java.lang.String receivedMessage = readReceivedMessage(inData);
                ParsedMessage parsedMessage = parseReceivedMessage(receivedMessage);
                //TODO: Use strategy or state pattern to get rid of switch/case.
                switch (parsedMessage.getCommand()) {
                    case HI:
                        clientName = parsedMessage.getClientName();
                        sendMessage(outData, "HI " + clientName);
                        break;
                    case BYE:
                        long endTime = System.nanoTime();
                        long duration = (endTime - startTime) / 1000000;
                        sendMessage(outData, java.lang.String.format("BYE %s, WE SPOKE FOR %d MS", clientName, duration));
                        break;
                    case ADD_NODE:
                        try {
                            graph.addNode(new Node(parsedMessage.getSourceNodeName()));
                            sendMessage(outData, "NODE ADDED");
                        } catch (NodeAlreadyExistsException ex) {
                            System.out.println("Node already exists");
                            sendMessage(outData, "ERROR: NODE ALREADY EXISTS");
                        }
                        break;
                    case REMOVE_NODE:
                        try {
                            graph.removeNode(new Node(parsedMessage.getSourceNodeName()));
                            sendMessage(outData, "NODE REMOVED");
                        } catch (NodeNotFoundException ex) {
                            System.out.println("ERROR: NODE NOT FOUND");
                            sendMessage(outData, "ERROR: NODE NOT FOUND");
                        }
                        break;
                    case ADD_EDGE:
                        try {
                            graph.addEdge(new Node(parsedMessage.getSourceNodeName()),
                                    new Node(parsedMessage.getDestinationNodeName()),
                                    parsedMessage.getWeight());
                            sendMessage(outData, "EDGE ADDED");
                        } catch (NodeNotFoundException ex) {
                            System.out.println("ERROR: NODE NOT FOUND");
                            sendMessage(outData, "ERROR: NODE NOT FOUND");
                        }
                        break;
                    case REMOVE_EDGE:
                        try {
                            graph.removeEdge(new Node(parsedMessage.getSourceNodeName()),
                                    new Node(parsedMessage.getDestinationNodeName()));
                            sendMessage(outData, "EDGE REMOVED");
                        } catch (NodeNotFoundException ex) {
                            System.out.println("ERROR: NODE NOT FOUND");
                            sendMessage(outData, "ERROR: NODE NOT FOUND");
                        }
                        break;
                    case INVALID:
                        sendMessage(outData, "SORRY, I DID NOT UNDERSTAND THAT");
                        break;
                    default:
                        sendMessage(outData, "SORRY, I DID NOT UNDERSTAND THAT");
                }
            }
        } catch (SocketTimeoutException ex) {
            if (outData != null) {
                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 1000000;
                sendMessage(outData, java.lang.String.format("BYE %s, WE SPOKE FOR %d MS", clientName, duration));
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

    private static void handshake(PrintWriter outData) {
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
