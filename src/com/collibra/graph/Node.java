package com.collibra.graph;

import java.util.*;

/**
 * Represents graph node.
 */
public class Node {
    private final String name;

    private final Map<Node, List<Integer>> adjacentNodes = new HashMap<>();

    public Node(String name) {
        if (!name.matches("^[A-Za-z0-9\\-]+$")) {
            throw new IllegalArgumentException("Node name should contain valid characters.");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Map<Node, List<Integer>> getAdjacentNodes() {
        return adjacentNodes;
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
