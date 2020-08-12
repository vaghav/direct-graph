package com.collibra.graph;

class Edge {
    private final Node destination;
    private final int weight;

    public Edge( Node destination, int weight) {
        this.destination = destination;
        this.weight = weight;
    }

    public Node getDestination() {
        return destination;
    }

    public int getWeight() {
        return weight;
    }
}
