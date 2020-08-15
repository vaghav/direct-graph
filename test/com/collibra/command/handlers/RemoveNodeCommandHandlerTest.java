package com.collibra.command.handlers;

import com.collibra.graph.Graph;
import com.collibra.graph.GraphImpl;
import com.collibra.graph.Node;
import com.collibra.message.util.SessionContext;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;

import static org.assertj.core.api.Assertions.assertThat;

class RemoveNodeCommandHandlerTest {

    @Test
    void handleCommand_removesNode() throws Exception {
        Graph graph = new GraphImpl();
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        graph.addNodes(ImmutableList.of(nodeA, nodeB, nodeC));
        CommandHandler addEdgeHandler = new AddEdgeCommandHandler(graph);
        addEdgeHandler.handleCommand(new PrintWriter("ddd"),
                "ADD EDGE A B 3", new SessionContext());
        addEdgeHandler.handleCommand(new PrintWriter("ddd"),
                "ADD EDGE B A 3", new SessionContext());
        addEdgeHandler.handleCommand(new PrintWriter("ddd"),
                "ADD EDGE A C 4", new SessionContext());
        addEdgeHandler.handleCommand(new PrintWriter("ddd"),
                "ADD EDGE B C 4", new SessionContext());
        CommandHandler removeNodeHandler = new RemoveNodeCommandHandler(graph);
        removeNodeHandler.handleCommand(new PrintWriter("ddd"),
                "REMOVE NODE A", new SessionContext());

        assertThat(graph.getNodes().size()).isEqualTo(2);
        assertThat(graph.getNode("B").getAdjacentNodes().size()).isEqualTo(1);
    }
}