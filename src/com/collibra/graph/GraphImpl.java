package com.collibra.graph;

import java.util.*;

/**
 * Implementation of directed graph API.
 */
public class GraphImpl implements Graph {
    private Map<String, Node> nameToNodes = new HashMap<>();
    private Map<Node, LinkedList<Edge>> adjacencyList = new HashMap<>();

    @Override
    public void addNode(Node node) {
        if (nameToNodes.putIfAbsent(node.getName(), node) != null) {
            throw new IllegalStateException("ERROR: NODE ALREADY EXISTS.");
        }
    }

    @Override
    public void addEdge(Node source, Node destination, int weight) {
        Node foundSourceNode = nameToNodes.get(source.getName());
        Node foundDestinationNode = nameToNodes.get(destination.getName());
        if (foundSourceNode == null || foundDestinationNode == null) {
            throw new IllegalStateException("ERROR: NODE NOT FOUND.");
        }
        adjacencyList.get(foundSourceNode.getName()).addFirst(new Edge(source, destination, weight));
    }
}
