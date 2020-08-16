package com.collibra.graph;

import com.collibra.exceptions.NodeAlreadyExistsException;
import com.collibra.exceptions.NodeNotFoundException;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Exposes the API for directed graph.
 */
public class GraphImpl implements Graph {
    private final Map<String, Node> nameToNodeMap = new HashMap<>();

    @Override
    public Collection<Node> getNodes() {
        return nameToNodeMap.values();
    }

    @Override
    public synchronized void addNode(String nodeName) throws NodeAlreadyExistsException {
        if (nameToNodeMap.containsKey(nodeName)) {
            throw new NodeAlreadyExistsException("Node already exists in the graph: '" + nodeName + "'");
        }
        nameToNodeMap.put(nodeName, new Node(nodeName));
    }

    @Override
    public synchronized void removeNode(String nodeName) throws NodeNotFoundException {
        if (!nameToNodeMap.containsKey(nodeName)) {
            throw new NodeNotFoundException("Node doesn't exist in the graph: '" + nodeName + "'");
        }
        Node node = new Node(nodeName);
        nameToNodeMap.values().forEach(vertex -> vertex.getAdjacentNodes().remove(node));
        nameToNodeMap.remove(nodeName);
    }

    //TODO: Change the parameter to List<String> and refactor unit tests.
    @Override
    public synchronized void addNodes(List<Node> nodes) {
        nodes.forEach(node -> nameToNodeMap.put(node.getName(), node));
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
        Node node = nameToNodeMap.get(nodeName);
        if (node == null) {
            throw new NodeNotFoundException("Node doesn't exist in the graph: '" + nodeName + "'");
        }
        return node;
    }
}
