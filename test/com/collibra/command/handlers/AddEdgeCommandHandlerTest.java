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

class AddEdgeCommandHandlerTest {

    @Test
    void handleCommand_addsEdge() throws Exception {
        Graph graph = new GraphImpl();
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        graph.addNodes(ImmutableList.of(nodeA, nodeB, nodeC));
        CommandHandler addEdgeHandler = new AddEdgeCommandHandler(graph);
        addEdgeHandler.handleCommand(new PrintWriter("ddd"),
                "ADD EDGE A B 3", new SessionContext());
        addEdgeHandler.handleCommand(new PrintWriter("ddd"),
                "ADD EDGE A C 4", new SessionContext());
        addEdgeHandler.handleCommand(new PrintWriter("ddd"),
                "ADD EDGE B C 4", new SessionContext());

        assertThat(nodeA.getAdjacentNodes().size()).isEqualTo(2);
        assertThat(nodeB.getAdjacentNodes().size()).isEqualTo(1);
    }

    @Test
    public void handleCommand_invalidArguments_throwsException() {
        GraphImpl graph = new GraphImpl();
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        graph.addNodes(ImmutableList.of(nodeA, nodeB));
        CommandHandler addEdgeHandler = new AddEdgeCommandHandler(graph);
        assertThatThrownBy(() ->
                addEdgeHandler.handleCommand(new PrintWriter("ddd"),
                        "ADD APPLE A B 3", new SessionContext()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ADD APPLE A B 3] is not valid command for edge adding");
    }
}