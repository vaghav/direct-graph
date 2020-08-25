package com.collibra.command.handlers;

import com.collibra.exceptions.NodeNotFoundException;
import com.collibra.graph.Graph;
import com.collibra.message.util.SessionContext;

import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.collibra.message.util.CommunicationUtil.sendMessage;

/**
 * Implements remove edge command handler.
 */
public class RemoveEdgeCommandHandler implements CommandHandler {
    private static final Pattern removeEdgePattern = Pattern.compile("^REMOVE EDGE (?<fromNodeName>[A-Za-z0-9\\-]+) " +
            "(?<toNodeName>[A-Za-z0-9\\-]+)$");
    private final Graph graph;

    public RemoveEdgeCommandHandler(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void handleCommand(PrintWriter outData, String receivedMessage, SessionContext sessionContext) {
        Matcher removeEdgeMatcher = removeEdgePattern.matcher(receivedMessage);
        if (!removeEdgeMatcher.find()) {
            throw new IllegalArgumentException(String.format("[%s] is not valid command for edge removal",
                    receivedMessage));
        }
        try {
            graph.removeEdge(removeEdgeMatcher.group("fromNodeName"),
                    removeEdgeMatcher.group("toNodeName"));
            sendMessage(outData, "EDGE REMOVED");
        } catch (NodeNotFoundException ex) {
            System.out.println(ex.getMessage());
            sendMessage(outData, "ERROR: NODE NOT FOUND");
        }
    }
}
