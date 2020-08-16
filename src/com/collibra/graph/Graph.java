package com.collibra.graph;

import com.collibra.exceptions.NodeAlreadyExistsException;
import com.collibra.exceptions.NodeNotFoundException;

import java.util.Collection;
import java.util.List;

/**
 * Exposes the API for graph creation and processing.
 */
public interface Graph {

    /**
     * Return graph nodes.
     */
    Collection<Node> getNodes();

    /**
     * Adds node to the graph with the provided node name.
     */
    void addNode(String nodeName) throws NodeAlreadyExistsException;

    /**
     * Adds nodes to the graph with provided node.
     */
    void addNodes(List<Node> nodes);

    /**
     * Adds edge from source to destination node with a given weight.
     */
    void addEdge(String source, String destination, int weight) throws NodeNotFoundException;

    /**
     * Removes node by node name from the graph.
     */
    void removeNode(String nodeName) throws NodeNotFoundException;

    /**
     * Removes edge from the graph.
     */
    void removeEdge(String sourceNodeName, String destinationNodeName) throws NodeNotFoundException;

    /**
     * Retrieve node from the graph.
     *
     * @return the found node or throws exception.
     */
    Node getNode(String nodeName) throws NodeNotFoundException;
}
