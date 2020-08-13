package com.collibra.graph;

import com.collibra.exceptions.NodeAlreadyExistsException;
import com.collibra.exceptions.NodeNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of directed graph API.
 */
public class GraphImpl implements Graph {
    private final Set<Node> nodes = new HashSet<>();

    @Override
    public synchronized void addNode(Node node) throws NodeAlreadyExistsException {
        if (nodes.contains(node)) {
            throw new NodeAlreadyExistsException("ERROR: NODE ALREADY EXISTS");
        }
        nodes.add(node);
    }

    @Override
    public synchronized void removeNode(Node node) throws NodeNotFoundException {
        if (!nodes.contains(node)) {
            throw new NodeNotFoundException("ERROR: NODE NOT FOUND");
        }
        node.getAdjacentNodes().clear();
        nodes.remove(node);
    }

    @Override
    public synchronized void addNodes(List<Node> nodes) {
        this.nodes.addAll(nodes);
    }

    @Override
    public synchronized void addEdge(Node source, Node destination, int weight) throws NodeNotFoundException {
        checkNodeExistence(source);
        checkNodeExistence(destination);
        if (nodes.contains(source)) {
            source.getAdjacentNodes().putIfAbsent(destination, weight);
        }
    }

    @Override
    public synchronized void removeEdge(Node source, Node destination) throws NodeNotFoundException {
        checkNodeExistence(source);
        checkNodeExistence(destination);
        if (nodes.contains(source)) {
            source.getAdjacentNodes().remove(destination);
        }
    }

    private void checkNodeExistence(Node source) throws NodeNotFoundException {
        if (!nodes.contains(source)) {
            throw new NodeNotFoundException("ERROR: NODE NOT FOUND.");
        }
    }

    //TODO: Rewrite unit tests and remove the method
    Set<Node> getNodes() {
        return nodes;
    }
}
