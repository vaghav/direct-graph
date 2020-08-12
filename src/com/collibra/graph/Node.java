package com.collibra.graph;

import java.util.*;

/**
 * Represents graph node.
 */
public class Node {
    private final String name;

    private final Map<Node, Integer> adjacentNodes = new HashMap<>();

    private List<Node> shortestPath = new ArrayList<>();

    private Integer distance = Integer.MAX_VALUE;

    public Node(String name) {
        if (!name.matches("^[A-Za-z0-9\\-]+$")) {
            throw new IllegalArgumentException("Node name should contain valid characters.");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setShortestPath(List<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public List<Node> getShortestPath() {
        return shortestPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return name.equals(node.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
