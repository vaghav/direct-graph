package com.collibra.graph.api;

import com.collibra.graph.Node;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GraphServiceImplTest {

    @Test
    public void findShortestPath_returnsShortestPathValue() {
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        nodeA.getAdjacentNodes().put(nodeB, 10);
        nodeA.getAdjacentNodes().put(nodeC, 15);
        nodeB.getAdjacentNodes().put(nodeC, 3);
        GraphServiceImpl service = new GraphServiceImpl();
        Integer shortestPath = service.findShortestPath(nodeA, nodeC);

        assertThat(shortestPath).isEqualTo(13);
    }

    @Test
    public void findShortestPath_returnsShortestPathValueCase2() {
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        nodeA.getAdjacentNodes().put(nodeB, 10);
        nodeA.getAdjacentNodes().put(nodeC, 9);
        nodeB.getAdjacentNodes().put(nodeC, 3);

        GraphServiceImpl service = new GraphServiceImpl();
        Integer shortestPath = service.findShortestPath(nodeA, nodeC);

        assertThat(shortestPath).isEqualTo(9);
    }

    @Test
    public void findShortestPath_returnsShortestPathValueCase3() {
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        nodeA.getAdjacentNodes().put(nodeB, 10);
        nodeA.getAdjacentNodes().put(nodeC, 9);
        nodeB.getAdjacentNodes().put(nodeC, 3);
        GraphServiceImpl service = new GraphServiceImpl();
        Integer shortestPath = service.findShortestPath(nodeA, nodeC);

        assertThat(shortestPath).isEqualTo(9);
    }
}