package com.collibra.server;

import com.collibra.command.handlers.*;
import com.collibra.graph.Graph;
import com.collibra.graph.GraphImpl;
import com.collibra.graph.api.GraphServiceImpl;
import com.collibra.message.util.Command;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.collibra.message.util.Command.*;

/**
 * Implements simple TCP/IP socket server.
 */
public class Server {

    private static final int PORT_NUMBER = 50000;
    private static final int SO_TIMEOUT_MS = 30000;

    public static void main(String[] args) throws IOException {
        try (ServerSocket listener = new ServerSocket(PORT_NUMBER)) {
            System.out.println("The server is running...");
            Socket socket;
            while (true) {
                socket = listener.accept();
                System.out.println("Accepted a connection");
                socket.setSoTimeout(SO_TIMEOUT_MS);
                Graph graph = new GraphImpl();
                Map<Command, CommandHandler> commandToHandler = new ConcurrentHashMap<>();
                commandToHandler.put(HI, new GreetingCommandHandler());
                commandToHandler.put(ADD_NODE, new AddNodeCommandHandler(graph));
                commandToHandler.put(REMOVE_NODE, new RemoveNodeCommandHandler(graph));
                commandToHandler.put(ADD_EDGE, new AddEdgeCommandHandler(graph));
                commandToHandler.put(REMOVE_EDGE, new RemoveEdgeCommandHandler(graph));
                commandToHandler.put(FIND_SHORTEST_PATH, new ShortestPathCommandHandler(new GraphServiceImpl(graph)));
                commandToHandler.put(INVALID, new InvalidCommandHandler());
                new ServerThread(socket, commandToHandler).start();
            }
        }
    }
}




