package com.collibra.command.handlers;

import com.collibra.exceptions.NodeNotFoundException;
import com.collibra.graph.Graph;
import com.collibra.graph.Node;

import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.collibra.message.util.CommunicationUtil.sendMessage;

/**
 * Greeting node command handler implementation.
 */
public class RemoveNodeCommandHandler implements CommandHandler {
    private static final Pattern removeNodePattern = Pattern.compile("^REMOVE NODE (?<nodeName>[A-Za-z0-9\\-]+)$");
    private final Graph graph;

    public RemoveNodeCommandHandler(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void handleCommand(PrintWriter outData, String receivedMessage) {
        Matcher removeNodeMatcher = removeNodePattern.matcher(receivedMessage);
        if (removeNodeMatcher.find()) {
            try {
                graph.removeNode(new Node(removeNodeMatcher.group("nodeName")));
                sendMessage(outData, "NODE REMOVED");
            } catch (NodeNotFoundException ex) {
                System.out.println("ERROR: NODE NOT FOUND");
                sendMessage(outData, "ERROR: NODE NOT FOUND");
            }
        }
    }
}
