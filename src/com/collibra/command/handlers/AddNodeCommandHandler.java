package com.collibra.command.handlers;

import com.collibra.exceptions.NodeAlreadyExistsException;
import com.collibra.graph.Graph;
import com.collibra.graph.Node;
import com.collibra.message.util.ParsedMessage;

import java.io.PrintWriter;

import static com.collibra.message.util.MessageUtil.sendMessage;

/**
 * Add node command handler implementation.
 */
public class AddNodeCommandHandler implements CommandHandler {
    private final Graph graph;

    public AddNodeCommandHandler(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void handleCommand(PrintWriter outData, ParsedMessage parsedMessage) {
        try {
            graph.addNode(new Node(parsedMessage.getSourceNodeName()));
            sendMessage(outData, "NODE ADDED");
        } catch (NodeAlreadyExistsException ex) {
            System.out.println("Node already exists");
            sendMessage(outData, "ERROR: NODE ALREADY EXISTS");
        }
    }
}
