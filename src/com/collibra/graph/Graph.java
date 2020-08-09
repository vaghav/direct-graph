package com.collibra.graph;

/**
 * Expose API for graph creation and processing.
 */
public interface Graph {

    /** Add node to the graph with provided name */
    void addNode(Node name);

    /** Add edge from source to destination node with a given weight */
    void addEdge(Node source, Node destination, int weight);
}
