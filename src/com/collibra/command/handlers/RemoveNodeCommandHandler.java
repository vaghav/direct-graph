package com.collibra.command.handlers;

import com.collibra.exceptions.NodeNotFoundException;
import com.collibra.graph.Graph;
import com.collibra.graph.Node;
import com.collibra.message.util.ParsedMessage;

import java.io.PrintWriter;

import static com.collibra.message.util.MessageUtil.sendMessage;

/**
 * Greeting node command handler implementation.
 */
public class RemoveNodeCommandHandler implements CommandHandler {
    private final Graph graph;

    public RemoveNodeCommandHandler(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void handleCommand(PrintWriter outData, ParsedMessage parsedMessage) {
        try {
            graph.removeNode(new Node(parsedMessage.getSourceNodeName()));
            sendMessage(outData, "NODE REMOVED");
        } catch (NodeNotFoundException ex) {
            System.out.println("ERROR: NODE NOT FOUND");
            sendMessage(outData, "ERROR: NODE NOT FOUND");
        }
    }
}
