package com.collibra.graph.api;

import com.collibra.exceptions.NodeNotFoundException;

/**
 * Exposes the API for direct graph.
 */
public interface GraphService {
    /**
     * @param sourceNodeName      the starting node
     * @param destinationNodeName the destination node
     * @return the value of the shortest path from source to destination
     */
    Integer findShortestPath(String sourceNodeName, String destinationNodeName) throws NodeNotFoundException;
}
