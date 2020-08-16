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
    public synchronized void addNode(String nodeName) throws NodeAlreadyExistsException {
        Node node = new Node(nodeName);
        if (nodes.contains(node)) {
            throw new NodeAlreadyExistsException("Node already exists in the graph: '" + nodeName + "'");
        }
        nodes.add(node);
    }

    @Override
    public synchronized void removeNode(String nodeName) throws NodeNotFoundException {
        Node node = new Node(nodeName);
        if (!nodes.contains(node)) {
            throw new NodeNotFoundException("Node doesn't exist in the graph: '" + nodeName + "'");
        }
        nodes.forEach(vertex -> vertex.getAdjacentNodes().remove(node));
        nodes.remove(node);
    }

    //TODO: Change the parameter to List<String> and refactor unit tests.
    @Override
    public synchronized void addNodes(List<Node> nodes) {
        this.nodes.addAll(nodes);
    }

    @Override
    public synchronized void addEdge(String sourceNodeName, String destinationNodeName, int weight)
            throws NodeNotFoundException {
        Node sourceNode = getNode(sourceNodeName);
        Node destNode = getNode(destinationNodeName);
        System.out.printf("source: [%s], destination: [%s]%n", sourceNode.getName(), destNode.getName());
        sourceNode.getAdjacentNodes().putIfAbsent(destNode, weight);
    }

    @Override
    public synchronized void removeEdge(String sourceNodeName, String destinationNodeName)
            throws NodeNotFoundException {
        Node sourceNode = getNode(sourceNodeName);
        Node destNode = getNode(destinationNodeName);
        sourceNode.getAdjacentNodes().remove(destNode);
    }

    @Override
    public Node getNode(String nodeName) throws NodeNotFoundException {
        return nodes.stream().filter(node -> node.getName().equals(nodeName)).findFirst()
                .orElseThrow(() -> new NodeNotFoundException("Node doesn't exist in the graph: '" + nodeName + "'"));
    }
}
