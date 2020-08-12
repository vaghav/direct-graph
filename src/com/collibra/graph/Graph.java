package com.collibra.graph;

import java.util.List;

/**
 * Expose API for graph creation and processing.
 */
public interface Graph {

    /**
     * Add node to the graph with provided node
     */
    void addNode(Node node);

    /**
     * Add nodes to the graph with provided node
     */
    void addNodes(List<Node> nodes);

    /**
     * Add edge from source to destination node with a given weight
     */
    void addEdge(Node source, Node destination, int weight);

    void removeNode(Node node);

    void removeEdge(Node source, Node destination);
}
