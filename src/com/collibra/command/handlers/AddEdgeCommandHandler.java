package com.collibra.command.handlers;

import com.collibra.exceptions.NodeNotFoundException;
import com.collibra.graph.Graph;
import com.collibra.graph.Node;

import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.collibra.message.util.CommunicationUtil.sendMessage;

/**
 * Implements add edge command handler.
 */
public class AddEdgeCommandHandler implements CommandHandler {
    private static final Pattern addEdgePattern = Pattern.compile("^ADD EDGE (?<fromNodeName>[A-Za-z0-9\\-]+) " +
            "(?<toNodeName>[A-Za-z0-9\\-]+) (?<weight>[0-9]+)$");
    private final Graph graph;

    public AddEdgeCommandHandler(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void handleCommand(PrintWriter outData, String receivedMessage) {
        Matcher addEdgeMatcher = addEdgePattern.matcher(receivedMessage);
        if (!addEdgeMatcher.find()) {
            throw new IllegalArgumentException(String.format("[%s] is not valid command for edge adding",
                    receivedMessage));
        }
        try {
            graph.addEdge(new Node(addEdgeMatcher.group("fromNodeName")),
                    new Node(addEdgeMatcher.group("toNodeName")),
                    Integer.parseInt(addEdgeMatcher.group("weight")));
            sendMessage(outData, "EDGE ADDED");
        } catch (NodeNotFoundException ex) {
            System.out.println("Node doesn't exist in the graph: " + ex.getMessage());
            sendMessage(outData, "ERROR: NODE NOT FOUND");
        }
    }
}
