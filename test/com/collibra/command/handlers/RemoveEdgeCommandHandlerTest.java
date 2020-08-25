package com.collibra.command.handlers;

import com.collibra.graph.Graph;
import com.collibra.graph.GraphImpl;
import com.collibra.graph.Node;
import com.collibra.message.util.SessionContext;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RemoveEdgeCommandHandlerTest {

    @Test
    void handleCommand_removeEdge() throws Exception {
        Graph graph = new GraphImpl();
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        graph.addNodes(ImmutableList.of(nodeA, nodeB, nodeC));
        CommandHandler removeEdgeHandler = new RemoveEdgeCommandHandler(graph);
        CommandHandler addEdgeHandler = new AddEdgeCommandHandler(graph);
        addEdgeHandler.handleCommand(new PrintWriter("ddd"),
                "ADD EDGE A B 3", new SessionContext());
        addEdgeHandler.handleCommand(new PrintWriter("ddd"),
                "ADD EDGE A C 4", new SessionContext());
        removeEdgeHandler.handleCommand(new PrintWriter("ddd"),
                "REMOVE EDGE A C", new SessionContext());

        assertThat(nodeA.getAdjacentNodes().size()).isEqualTo(1);
    }

    @Test
    public void handleCommand_invalidArguments_throwsException() {
        GraphImpl graph = new GraphImpl();
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        graph.addNodes(ImmutableList.of(nodeA, nodeB));
        CommandHandler removeEdgeHandler = new RemoveEdgeCommandHandler(graph);
        assertThatThrownBy(() ->
                removeEdgeHandler.handleCommand(new PrintWriter("ddd"),
                        "REMOVE APPLE A B 3", new SessionContext()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[REMOVE APPLE A B 3] is not valid command for edge removal");
    }
}
