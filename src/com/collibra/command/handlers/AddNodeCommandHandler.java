package com.collibra.command.handlers;

import com.collibra.exceptions.NodeAlreadyExistsException;
import com.collibra.graph.Graph;
import com.collibra.graph.Node;

import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.collibra.message.util.CommunicationUtil.sendMessage;

/**
 * Add node command handler implementation.
 */
public class AddNodeCommandHandler implements CommandHandler {
    private static final Pattern addNodePattern = Pattern.compile("^ADD NODE (?<nodeName>[A-Za-z0-9\\-]+)$");
    private final Graph graph;

    public AddNodeCommandHandler(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void handleCommand(PrintWriter outData, String receivedMessage) {
        Matcher addNodeMatcher = addNodePattern.matcher(receivedMessage);
        if (addNodeMatcher.find()) {
            try {
                graph.addNode(new Node(addNodeMatcher.group("nodeName")));
                sendMessage(outData, "NODE ADDED");
            } catch (NodeAlreadyExistsException ex) {
                System.out.println("Node already exists");
                sendMessage(outData, "ERROR: NODE ALREADY EXISTS");
            }
        }
    }
}
