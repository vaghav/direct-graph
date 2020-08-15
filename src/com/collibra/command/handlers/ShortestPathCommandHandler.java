package com.collibra.command.handlers;

import com.collibra.exceptions.NodeNotFoundException;
import com.collibra.graph.api.GraphService;
import com.collibra.message.util.SessionContext;

import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.collibra.message.util.CommunicationUtil.sendMessage;

/**
 * Find shortest path handler.
 */
public class ShortestPathCommandHandler implements CommandHandler {
    private final GraphService graphService;
    private static final Pattern shortestPathPattern = Pattern.compile("^SHORTEST PATH (?<fromNodeName>[A-Za-z0-9\\-]+)" +
            " (?<toNodeName>[A-Za-z0-9\\-]+)$");

    public ShortestPathCommandHandler(GraphService graphService) {
        this.graphService = graphService;
    }

    @Override
    public void handleCommand(PrintWriter outData, String receivedMessage, SessionContext sessionContext) {
        Matcher shortestPathMatcher = shortestPathPattern.matcher(receivedMessage);
        if (!shortestPathMatcher.find()) {
            throw new IllegalArgumentException(String.format("Received invalid arguments for finding short path. " +
                    "Arguments: [%s]", receivedMessage));
        }
        try {
            Integer shortestPath = graphService.findShortestPath(shortestPathMatcher.group("fromNodeName"),
                    shortestPathMatcher.group("toNodeName"));
            sendMessage(outData, shortestPath.toString());
        } catch (NodeNotFoundException ex) {
            System.out.println(ex.getMessage());
            sendMessage(outData, "ERROR: NODE NOT FOUND");
        }
    }
}
