package com.zendesk.company.routeplanner.entity.graph;

import lombok.Data;

/**
 * Edge class implements standard properties and methods for a weighted edge in a directed graph.
 */
@Data
public class Edge {
    private Node source;
    private Node sink;
    private double weight = 1.0;

    public Edge(Node source, Node sink) {
        this.source = source;
        this.sink = sink;
    }

    public Edge(Node source, Node sink, double weight) {
        this.source = source;
        this.sink = sink;
        this.weight = weight;
    }
}