package com.collibra.graph.api;

import com.collibra.exceptions.NodeNotFoundException;
import com.collibra.graph.Graph;
import com.collibra.graph.Node;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implements direct graph API.
 */
public class GraphServiceImpl implements GraphService {
    private static final int NODE_DISTANCE_TO_ITSELF = 0;
    private final Graph graph;

    public GraphServiceImpl(Graph graph) {
        this.graph = graph;
    }

    @Override
    public synchronized Integer findShortestPath(String sourceNodeName, String destinationNodeName)
            throws NodeNotFoundException {
        Node destinationNod = graph.getNode(destinationNodeName);
        return getNodeToDistanceMap(sourceNodeName).get(destinationNod);
    }

    @Override
    public synchronized String findClosestNeighbours(String sourceNodeName, int weight)
            throws NodeNotFoundException {
        Map<Node, Integer> nodeToDistanceMap = getNodeToDistanceMap(sourceNodeName);
        return nodeToDistanceMap.entrySet().stream()
                .filter(entry -> entry.getValue() < weight)
                .filter(entry -> !entry.getKey().getName().equals(sourceNodeName))
                .map(entry -> entry.getKey().getName())
                .sorted()
                .collect(Collectors.joining(","));
    }

    private static void trackShortestPath(Map<Node, List<Node>> nodeToShortestPath, Node processingNode) {
        List<Node> shortestPath = new LinkedList<>(nodeToShortestPath.get(processingNode));
        shortestPath.add(processingNode);
        nodeToShortestPath.put(processingNode, shortestPath);
    }

    private Map<Node, Integer> getNodeToDistanceMap(String sourceNode) throws NodeNotFoundException {
        Node source = graph.getNode(sourceNode);
        // Track shortest path for future if needed.
        Map<Node, List<Node>> nodeToShortestPath = new HashMap<>();
        graph.getNodes().forEach(node -> nodeToShortestPath.put(node, new LinkedList<>()));

        Map<Node, Integer> nodeToDistance = new HashMap<>();
        graph.getNodes().forEach(node -> nodeToDistance.put(node, Integer.MAX_VALUE));

        nodeToDistance.put(source, NODE_DISTANCE_TO_ITSELF);
        // Nodes already in shortest path.
        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);
        while (!unsettledNodes.isEmpty()) {
            Node processingNode = getLowestDistanceNode(unsettledNodes, nodeToDistance);
            unsettledNodes.remove(processingNode);
            settledNodes.add(processingNode);
            for (Map.Entry<Node, Integer> adjacencyPair : processingNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    int newDistance = nodeToDistance.get(processingNode) + edgeWeight;
                    int currentDistance = nodeToDistance.get(adjacentNode);
                    if (newDistance < currentDistance) {
                        nodeToDistance.put(adjacentNode, newDistance);
                        unsettledNodes.add(adjacentNode);
                        trackShortestPath(nodeToShortestPath, processingNode);
                    }
                }
            }
        }
        return nodeToDistance;
    }

    private static Node getLowestDistanceNode(Set<Node> unsettledNodes,
                                              Map<Node, Integer> nodeToDistanceMap) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node : unsettledNodes) {
            int nodeDistance = nodeToDistanceMap.get(node);
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

}
