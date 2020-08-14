package com.collibra.graph;

import com.collibra.exceptions.NodeAlreadyExistsException;
import com.collibra.exceptions.NodeNotFoundException;

import java.util.List;
import java.util.Set;

/**
 * Exposes the API for graph creation and processing.
 */
public interface Graph {

    /**
     * Return graph nodes.
     */
    Set<Node> getNodes();

    /**
     * Adds node to the graph with provided node.
     */
    void addNode(Node node) throws NodeAlreadyExistsException;

    /**
     * Adds nodes to the graph with provided node.
     */
    void addNodes(List<Node> nodes);

    /**
     * Adds edge from source to destination node with a given weight.
     */
    void addEdge(Node source, Node destination, int weight) throws NodeNotFoundException;

    /**
     * Removes provided node from the graph.
     */
    void removeNode(Node node) throws NodeNotFoundException;

    /**
     * Removes edge from the graph.
     */
    void removeEdge(Node source, Node destination) throws NodeNotFoundException;
}
