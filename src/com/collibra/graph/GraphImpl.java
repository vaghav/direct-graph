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
    public void addNode(Node node) throws NodeAlreadyExistsException {
        if (nodes.contains(node)) {
            throw new NodeAlreadyExistsException("ERROR: NODE ALREADY EXISTS");
        }
        nodes.add(node);
    }

    @Override
    public void removeNode(Node node) throws NodeNotFoundException {
        if (!nodes.contains(node)) {
            throw new NodeNotFoundException("ERROR: NODE NOT FOUND");
        }
        node.getAdjacentNodes().clear();
        nodes.remove(node);
    }

    @Override
    public void addNodes(List<Node> nodes) {
        this.nodes.addAll(nodes);
    }

    @Override
    public void addEdge(Node source, Node destination, int weight) throws NodeNotFoundException {
        checkNodeExistence(source, destination);
        findNode(source).getAdjacentNodes().putIfAbsent(destination, weight);
    }

    @Override
    public void removeEdge(Node source, Node destination) throws NodeNotFoundException {
        checkNodeExistence(source, destination);
        findNode(source).getAdjacentNodes().remove(destination);
    }

    private void checkNodeExistence(Node source, Node destination) throws NodeNotFoundException {
        if (!nodes.contains(source) || !nodes.contains(destination)) {
            throw new NodeNotFoundException("ERROR: NODE NOT FOUND.");
        }
    }

    private Node findNode(Node source) {
        return nodes.stream().filter(node -> node.equals(source)).findFirst().get();
    }

    Set<Node> getNodes() {
        return nodes;
    }
}
