package com.collibra.command.handlers;

import com.collibra.exceptions.NodeNotFoundException;
import com.collibra.graph.Graph;
import com.collibra.graph.Node;
import com.collibra.message.util.ParsedMessage;

import java.io.PrintWriter;

import static com.collibra.message.util.MessageUtil.sendMessage;

/**
 * Add edge command handler implementation.
 */
public class AddEdgeCommandHandler implements CommandHandler {
    private final Graph graph;

    public AddEdgeCommandHandler(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void handleCommand(PrintWriter outData, ParsedMessage parsedMessage) {
        try {
            graph.addEdge(new Node(parsedMessage.getSourceNodeName()),
                    new Node(parsedMessage.getDestinationNodeName()),
                    parsedMessage.getWeight());
            sendMessage(outData, "EDGE ADDED");
        } catch (NodeNotFoundException ex) {
            System.out.println("ERROR: NODE NOT FOUND");
            sendMessage(outData, "ERROR: NODE NOT FOUND");
        }
    }
}
