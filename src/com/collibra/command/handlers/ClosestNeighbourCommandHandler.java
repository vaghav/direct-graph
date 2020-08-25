package com.collibra.command.handlers;

import com.collibra.exceptions.NodeNotFoundException;
import com.collibra.graph.api.GraphService;
import com.collibra.message.util.SessionContext;

import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.collibra.message.util.CommunicationUtil.sendMessage;

/**
 * Finds closes neighbours.
 */
public class ClosestNeighbourCommandHandler implements CommandHandler {
    private final GraphService graphService;
    private static final Pattern closestNeighboursPattern = Pattern.compile("^CLOSER THAN (?<weight>[0-9]+) " +
            "(?<nodeName>[A-Za-z0-9\\-]+)$");

    public ClosestNeighbourCommandHandler(GraphService graphService) {
        this.graphService = graphService;
    }

    @Override
    public void handleCommand(PrintWriter outData, String receivedMessage, SessionContext sessionContext) {
        Matcher matcher = closestNeighboursPattern.matcher(receivedMessage);
        if (!matcher.find()) {
            throw new IllegalArgumentException(String.format("Received invalid arguments for finding closest " +
                    "neighbours. Arguments: [%s]", receivedMessage));
        }
        try {
            String closestNeighbours = graphService.findClosestNeighbours(matcher.group("nodeName"),
                    Integer.parseInt(matcher.group("weight")));
            sendMessage(outData, closestNeighbours.trim());
        } catch (NodeNotFoundException ex) {
            System.out.println(ex.getMessage());
            sendMessage(outData, "ERROR: NODE NOT FOUND");
        }
    }
}
