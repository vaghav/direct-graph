package com.collibra.command.handlers;

import com.collibra.exceptions.NodeNotFoundException;
import com.collibra.graph.Graph;
import com.collibra.graph.Node;

import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.collibra.message.util.CommunicationUtil.sendMessage;

/**
 * Remove edge command handler implementation.
 */
public class RemoveEdgeCommandHandler implements CommandHandler {
    private static final Pattern removeEdgePattern = Pattern.compile("^REMOVE EDGE (?<fromNodeName>[A-Za-z0-9\\-]+) " +
            "(?<toNodeName>[A-Za-z0-9\\-]+)$");
    private final Graph graph;

    public RemoveEdgeCommandHandler(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void handleCommand(PrintWriter outData, String receivedMessage) {
        Matcher removeEdgeMatcher = removeEdgePattern.matcher(receivedMessage);
        if (removeEdgeMatcher.find()) {
            try {
                graph.removeEdge(new Node(removeEdgeMatcher.group("fromNodeName")),
                        new Node(removeEdgeMatcher.group("toNodeName")));
                sendMessage(outData, "EDGE REMOVED");
            } catch (NodeNotFoundException ex) {
                System.out.println("ERROR: NODE NOT FOUND");
                sendMessage(outData, "ERROR: NODE NOT FOUND");
            }
        }
    }
}
