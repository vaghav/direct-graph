package com.collibra.graph;

public class Node {
    private final String name;

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

}
