package com.collibra.graph.api;

import com.collibra.graph.Graph;
import com.collibra.graph.GraphImpl;
import com.collibra.graph.Node;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GraphServiceImplTest {

    @Test
    public void findShortestPath_returnsShortestPathValueCase1() throws Exception {
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        nodeA.getAdjacentNodes().put(nodeB, 10);
        nodeA.getAdjacentNodes().put(nodeC, 15);
        nodeB.getAdjacentNodes().put(nodeC, 3);
        Graph graph = new GraphImpl();
        graph.addNodes(ImmutableList.of(nodeA, nodeB, nodeC));
        GraphServiceImpl service = new GraphServiceImpl(graph);
        Integer shortestPath = service.findShortestPath("A", "C");

        assertThat(shortestPath).isEqualTo(13);
    }

    @Test
    public void findShortestPath_returnsShortestPathValueCase2() throws Exception {
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        nodeA.getAdjacentNodes().put(nodeB, 10);
        nodeA.getAdjacentNodes().put(nodeC, 9);
        nodeB.getAdjacentNodes().put(nodeC, 3);
        Graph graph = new GraphImpl();
        graph.addNodes(ImmutableList.of(nodeA, nodeB, nodeC));
        GraphServiceImpl service = new GraphServiceImpl(graph);
        Integer shortestPath = service.findShortestPath("A", "C");

        assertThat(shortestPath).isEqualTo(9);
    }

    @Test
    public void findShortestPath_returnsShortestPathValueCase3() throws Exception {
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        nodeA.getAdjacentNodes().put(nodeB, 10);
        nodeA.getAdjacentNodes().put(nodeC, 9);
        nodeB.getAdjacentNodes().put(nodeC, 3);
        Graph graph = new GraphImpl();
        graph.addNodes(ImmutableList.of(nodeA, nodeB, nodeC));
        GraphServiceImpl service = new GraphServiceImpl(graph);
        Integer shortestPath = service.findShortestPath("A", "C");

        assertThat(shortestPath).isEqualTo(9);
    }
}