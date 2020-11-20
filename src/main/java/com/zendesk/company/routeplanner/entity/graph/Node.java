package com.zendesk.company.routeplanner.entity.graph;

import lombok.Data;
import java.util.*;

/**
 * Node class implements a node in a directed graph.
 */
@Data
public class Node {
    private String label;

    private List<String> codes = new ArrayList<>();

    private Date openingDate;

    public Node(String label) {
        this.label = label;
    }

    public Node(String label, String code, Date openingDate) {
        this.label = label;
        codes.add(code);
        this.openingDate = openingDate;
    }

    public void addCode(String code) {
        codes.add(code);
    }
}