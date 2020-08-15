package com.collibra.graph;

import com.collibra.exceptions.NodeAlreadyExistsException;
import com.collibra.exceptions.NodeNotFoundException;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GraphImplTest {

    @Test
    public void addNode_succeeds() throws Exception {
        GraphImpl graph = new GraphImpl();
        Node node = new Node("A");
        graph.addNode("A");

        assertThat(graph.getNodes()).containsExactly(node);
    }

    @Test
    public void addNode_addExistingNode_throwsException() throws Exception {
        Graph graph = new GraphImpl();
        graph.addNode("A");
        assertThatThrownBy(() ->
                graph.addNode("A")).isInstanceOf(NodeAlreadyExistsException.class)
                .hasMessageContaining("Node already exists in the graph:");

    }

    @Test
    public void removeNode_succeeds() throws Exception {
        GraphImpl graph = new GraphImpl();
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        graph.addNodes(ImmutableList.of(nodeA, nodeB));
        graph.removeNode("B");

        assertThat(graph.getNodes()).containsExactly(nodeA);
    }

    @Test
    public void removeNode_noNode_throwsException() {
        GraphImpl graph = new GraphImpl();
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        graph.addNodes(ImmutableList.of(nodeA, nodeB));
        assertThatThrownBy(() ->
                graph.removeNode("C")).isInstanceOf(NodeNotFoundException.class)
                .hasMessageContaining("Node doesn't exist in the graph:");
    }

    @Test
    void addEdge_succeeds() throws Exception {
        GraphImpl graph = new GraphImpl();
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        graph.addNodes(Arrays.asList(nodeA, nodeB, nodeC));
        graph.addEdge("A", "B", 5);
        graph.addEdge("A", "C", 10);

        assertThat(graph.getNodes()).containsExactly(nodeA, nodeB, nodeC);
        assertThat(graph.getNodes().stream().findFirst().get().getAdjacentNodes().size()).isEqualTo(2);
    }

    @Test
    void removeEdge_succeeds() throws Exception {
        GraphImpl graph = new GraphImpl();
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        graph.addNodes(ImmutableList.of(nodeA, nodeB, nodeC));
        graph.addEdge("A", "B", 5);
        graph.addEdge("A", "C", 10);
        graph.removeEdge("A", "B");

        assertThat(graph.getNodes()).containsExactly(nodeA, nodeB, nodeC);
        assertThat(graph.getNodes().stream().findFirst().get().getAdjacentNodes().size()).isEqualTo(1);
    }
}