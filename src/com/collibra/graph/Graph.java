package com.collibra.graph;

import com.collibra.exceptions.NodeAlreadyExistsException;
import com.collibra.exceptions.NodeNotFoundException;

import java.util.List;

/**
 * Expose API for graph creation and processing.
 */
public interface Graph {

    /**
     * Add node to the graph with provided node.
     */
    void addNode(Node node) throws NodeAlreadyExistsException;

    /**
     * Add nodes to the graph with provided node.
     */
    void addNodes(List<Node> nodes);

    /**
     * Add edge from source to destination node with a given weight.
     */
    void addEdge(Node source, Node destination, int weight) throws NodeNotFoundException;

    /**
     * Remove provided node from the graph.
     */
    void removeNode(Node node) throws NodeNotFoundException;

    /**
     * Remove edge from the graph.
     */
    void removeEdge(Node source, Node destination) throws NodeNotFoundException;
}
