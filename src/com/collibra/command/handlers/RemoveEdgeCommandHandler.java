package com.collibra.command.handlers;

import com.collibra.exceptions.NodeNotFoundException;
import com.collibra.graph.Graph;
import com.collibra.graph.Node;
import com.collibra.message.util.ParsedMessage;

import java.io.PrintWriter;

import static com.collibra.message.util.MessageUtil.sendMessage;

/**
 * Remove edge command handler implementation.
 */
public class RemoveEdgeCommandHandler implements CommandHandler {
    private final Graph graph;

    public RemoveEdgeCommandHandler(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void handleCommand(PrintWriter outData, ParsedMessage parsedMessage) {
        try {
            graph.removeEdge(new Node(parsedMessage.getSourceNodeName()),
                    new Node(parsedMessage.getDestinationNodeName()));
            sendMessage(outData, "EDGE REMOVED");
        } catch (NodeNotFoundException ex) {
            System.out.println("ERROR: NODE NOT FOUND");
            sendMessage(outData, "ERROR: NODE NOT FOUND");
        }
    }
}
