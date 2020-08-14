package com.collibra.graph;

import com.collibra.exceptions.NodeAlreadyExistsException;
import com.collibra.exceptions.NodeNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Exposes the API for directed graph.
 */
public class GraphImpl implements Graph {
    private final Set<Node> nodes = new HashSet<>();

    @Override
    public Set<Node> getNodes() {
        return nodes;
    }

    @Override
    public synchronized void addNode(Node node) throws NodeAlreadyExistsException {
        if (nodes.contains(node)) {
            throw new NodeAlreadyExistsException("Node already exists in the graph: '" + node.getName() + "'");
        }
        nodes.add(node);
    }

    @Override
    public synchronized void removeNode(Node node) throws NodeNotFoundException {
        if (!nodes.contains(node)) {
            throw new NodeNotFoundException("Node doesn't exist in the graph: '" + node.getName() + "'");
        }
        nodes.forEach(vertex -> vertex.getAdjacentNodes().remove(node));
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
        source.getAdjacentNodes().putIfAbsent(destination, weight);
    }

    @Override
    public synchronized void removeEdge(Node source, Node destination) throws NodeNotFoundException {
        checkNodeExistence(source);
        checkNodeExistence(destination);
        source.getAdjacentNodes().remove(destination);
    }

    private void checkNodeExistence(Node node) throws NodeNotFoundException {
        if (!nodes.contains(node)) {
            throw new NodeNotFoundException("Node doesn't exist in the graph: '" + node.getName() + "'");
        }
    }
}
