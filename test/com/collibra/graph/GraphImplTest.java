package com.collibra.graph;

import com.collibra.exceptions.NodeAlreadyExistsException;
import com.collibra.exceptions.NodeNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GraphImplTest {

    @Test
    public void addNode_succeeds() throws NodeAlreadyExistsException {
        GraphImpl graph = new GraphImpl();
        Node node = new Node("A");
        graph.addNode(node);

        assertThat(graph.getNodes()).containsExactly(node);
    }

    @Test
    public void addNode_addExistingNode_throwsException() throws NodeAlreadyExistsException {
        Graph graph = new GraphImpl();
        graph.addNode(new Node("A"));
        assertThatThrownBy(() ->
                graph.addNode(new Node("A"))).isInstanceOf(NodeAlreadyExistsException.class)
                .hasMessageContaining("ERROR: NODE ALREADY EXISTS");

    }

    @Test
    public void removeNode_succeeds() throws NodeNotFoundException {
        GraphImpl graph = new GraphImpl();
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        graph.addNodes(Arrays.asList(nodeA, nodeB));
        graph.removeNode(nodeB);

        assertThat(graph.getNodes()).containsExactly(nodeA);
    }

    @Test
    public void removeNode_noNode_throwsException() {
        GraphImpl graph = new GraphImpl();
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        graph.addNodes(Arrays.asList(nodeA, nodeB));
        assertThatThrownBy(() ->
                graph.removeNode(new Node("C"))).isInstanceOf(NodeNotFoundException.class)
                .hasMessageContaining("ERROR: NODE NOT FOUND");
    }

    @Test
    void addEdge_succeeds() throws NodeNotFoundException {
        GraphImpl graph = new GraphImpl();
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        graph.addNodes(Arrays.asList(nodeA, nodeB, nodeC));
        graph.addEdge(nodeA, nodeB, 5);
        graph.addEdge(nodeA, nodeC, 10);

        assertThat(graph.getNodes()).containsExactly(nodeA, nodeB, nodeC);
        assertThat(graph.getNodes().stream().findFirst().get().getAdjacentNodes().size()).isEqualTo(2);
    }

    @Test
    void removeEdge_succeeds() throws NodeNotFoundException {
        GraphImpl graph = new GraphImpl();
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        graph.addNodes(Arrays.asList(nodeA, nodeB, nodeC));
        graph.addEdge(nodeA, nodeB, 5);
        graph.addEdge(nodeA, nodeC, 10);
        graph.removeEdge(nodeA, nodeB);

        assertThat(graph.getNodes()).containsExactly(nodeA, nodeB, nodeC);
        assertThat(graph.getNodes().stream().findFirst().get().getAdjacentNodes().size()).isEqualTo(1);
    }
}