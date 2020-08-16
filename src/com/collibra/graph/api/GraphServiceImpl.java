package com.collibra.graph.api;

import com.collibra.exceptions.NodeNotFoundException;
import com.collibra.graph.Graph;
import com.collibra.graph.Node;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implements direct graph API.
 */
public class GraphServiceImpl implements GraphService {
    private static final int QUEUE_INITIAL_CAPACITY = 11;
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
        PriorityQueue<Pair<Node, Integer>> unsettledNodesQueue = new PriorityQueue<>(QUEUE_INITIAL_CAPACITY,
                Comparator.comparingInt(Pair::getValue));

        unsettledNodesQueue.offer(new Pair<>(source, NODE_DISTANCE_TO_ITSELF));
        while (!unsettledNodesQueue.isEmpty()) {
            Node processingNode = unsettledNodesQueue.poll().getKey();
            settledNodes.add(processingNode);
            for (Map.Entry<Node, List<Integer>> adjacencyPair : processingNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer minWeight = adjacencyPair.getValue().stream().min(Comparator.naturalOrder()).get();
                if (!settledNodes.contains(adjacentNode)) {
                    int newDistance = nodeToDistance.get(processingNode) + minWeight;
                    int currentDistance = nodeToDistance.get(adjacentNode);
                    if (newDistance < currentDistance) {
                        nodeToDistance.put(adjacentNode, newDistance);
                        unsettledNodesQueue.offer(new Pair<>(adjacentNode, newDistance));
                        trackShortestPath(nodeToShortestPath, processingNode);
                    }
                }
            }
        }
        return nodeToDistance;
    }

    private static void trackShortestPath(Map<Node, List<Node>> nodeToShortestPath, Node processingNode) {
        List<Node> shortestPath = new LinkedList<>(nodeToShortestPath.get(processingNode));
        shortestPath.add(processingNode);
        nodeToShortestPath.put(processingNode, shortestPath);
    }
}
