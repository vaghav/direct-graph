package com.collibra.command.handlers;

import com.collibra.graph.Node;
import com.collibra.graph.api.GraphService;

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
    public void handleCommand(PrintWriter outData, String receivedMessage) {
        Matcher shortestPathMatcher = shortestPathPattern.matcher(receivedMessage);
        if (!shortestPathMatcher.find()) {
            throw new IllegalArgumentException(String.format("Received invalid arguments for finding short path. " +
                    "Arguments: [%s]", receivedMessage));
        }
        Integer shortestPath = graphService.findShortestPath(new Node(shortestPathMatcher.group("fromNodeName")),
                new Node(shortestPathMatcher.group("toNodeName")));
        sendMessage(outData, shortestPath.toString());
    }
}
