package com.collibra.graph.api;

import com.collibra.graph.Node;

/* Exposes API for direct graph. */
public interface GraphService {
 void findShortestPath(Node source, Node destination);
}
