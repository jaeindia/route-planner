package com.zendesk.company.routeplanner.service.graphoperations;

import com.zendesk.company.routeplanner.entity.graph.Edge;
import com.zendesk.company.routeplanner.entity.graph.Node;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

/**
 * Graph data store class.
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GraphHolder {
    private final Map<String, Node> codeMap = new HashMap<>();
    private final Map<String, Node> labelMap = new HashMap<>();
    private final Map<String, Edge> edgeMap = new HashMap<>();
    private final Map<String, String> edgeLabelCodeMap = new HashMap<>();
    private final Map<String, Set<String>> lineStationsMap = new HashMap<>();
    private final Map<String, List<String>> lineStationsSortedMap = new HashMap<>();

    private static GraphHolder instance;

    public static GraphHolder getInstance() {
        if (Objects.isNull(instance))
            instance = new GraphHolder();
        return instance;
    }

    public void clearLoadedGraph() {
        codeMap.clear();
        labelMap.clear();
        edgeMap.clear();
        edgeLabelCodeMap.clear();
        lineStationsMap.clear();
        lineStationsSortedMap.clear();
    }
}