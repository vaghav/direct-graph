package com.collibra.graph.api;

import com.collibra.graph.Node;

/**
 * Exposes API for direct graph.
 */
public interface GraphService {
    /**
     * @param source      node from which starting processing
     * @param destination node for which short path is found
     * @return the value of the shortest path from source to destination
     */
    Integer findShortestPath(Node source, Node destination);
}
