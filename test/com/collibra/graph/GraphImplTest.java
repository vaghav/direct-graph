package com.collibra.graph;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GraphImplTest {

    @Test
    public void addNode_succeeds() {
        GraphImpl graph = new GraphImpl();
        Node node = new Node("A");
        graph.addNode(node);

        assertThat(graph.getNodes()).containsExactly(node);
    }

    @Test
    public void addNode_addExistingNode_throwsException() {
        Graph graph = new GraphImpl();
        graph.addNode(new Node("A"));
        assertThatThrownBy(() ->
                graph.addNode(new Node("A"))).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("ERROR: NODE ALREADY EXISTS.");

    }

    @Test
    void addEdge_succeeds() {
        GraphImpl graph = new GraphImpl();
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        graph.addNodes(Arrays.asList(nodeA, nodeB, nodeC));
        graph.addEdge(nodeA, nodeB, 5);
        graph.addEdge(nodeA, nodeC, 10);

        assertThat(graph.getNodes()).containsExactly(nodeA, nodeB, nodeC);
    }
}