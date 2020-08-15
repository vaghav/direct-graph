package com.collibra.graph.api;

import com.collibra.exceptions.NodeNotFoundException;
import com.collibra.graph.Graph;
import com.collibra.graph.Node;
import javafx.util.Pair;

import java.util.*;

/**
 * Implements direct graph API.
 */
public class GraphServiceImpl implements GraphService {
    //TODO: Handle case for unconnected nodes.

    private static final int QUEUE_INITIAL_CAPACITY = 11;
    private static final int NODE_DISTANCE_TO_ITSELF = 0;
    private final Graph graph;

    public GraphServiceImpl(Graph graph) {
        this.graph = graph;
    }

    @Override
    public Integer findShortestPath(String sourceNode, String destinationNode) throws NodeNotFoundException {
        Node source = graph.getNode(sourceNode);
        Node destination = graph.getNode(destinationNode);
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
            for (Map.Entry<Node, Integer> adjacencyPair : processingNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    int newDistance = nodeToDistance.get(processingNode) + edgeWeight;
                    int currentDistance = nodeToDistance.get(adjacentNode);
                    if (newDistance < currentDistance) {
                        nodeToDistance.put(adjacentNode, newDistance);
                        unsettledNodesQueue.offer(new Pair<>(adjacentNode, newDistance));
                        //TODO: return when fond destination node
                        trackShortestPath(nodeToShortestPath, processingNode);
                    }
                }
            }
            settledNodes.add(processingNode);
            if (processingNode.getName().equals(destination.getName())) {
                return nodeToDistance.get(destination);
            }
        }
        return nodeToDistance.get(destination);
    }

    private static void trackShortestPath(Map<Node, List<Node>> nodeToShortestPath, Node processingNode) {
        List<Node> shortestPath = new LinkedList<>(nodeToShortestPath.get(processingNode));
        shortestPath.add(processingNode);
        nodeToShortestPath.put(processingNode, shortestPath);
    }
}
