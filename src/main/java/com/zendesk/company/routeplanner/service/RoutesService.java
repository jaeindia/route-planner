package com.zendesk.company.routeplanner.service;

import com.zendesk.company.routeplanner.entity.graph.Node;
import com.zendesk.company.routeplanner.entity.response.RouteResponseVo;
import com.zendesk.company.routeplanner.exception.BadRequestException;
import com.zendesk.company.routeplanner.graphoperations.GraphHolder;
import com.zendesk.company.routeplanner.graphoperations.ShortestPath;
import com.zendesk.company.routeplanner.util.Consts;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.List;

@Service
public class RoutesService {
    private final Map<String, Node> labelMap = GraphHolder.getInstance().getLabelMap();

    /**
     * Find the shortest path without travel time.
     *
     * @param sourceStation
     * @param destinationStation
     * @return
     */
    public RouteResponseVo findKShortestPaths(String sourceStation, String destinationStation) {
        // Validate request - nodes
        validateNodes(sourceStation, destinationStation);

        Node source = labelMap.get(sourceStation);
        Node destination = labelMap.get(destinationStation);
        ShortestPath shortestPath = new ShortestPath();
        return shortestPath.findKShortestPaths(source, destination, 1);
    }

    /**
     * Find K shortest paths with travel time (K = 3)
     *
     * @param sourceStation
     * @param destinationStation
     * @param date
     * @return
     */
    public List<RouteResponseVo> findKShortestPaths(String sourceStation, String destinationStation, Date date) {
        // Validate request - nodes
        validateNodes(sourceStation, destinationStation);

        Node source = labelMap.get(sourceStation);
        Node destination = labelMap.get(destinationStation);
        ShortestPath shortestPath = new ShortestPath();
        return shortestPath.findKShortestPaths(source, destination, Consts.MAX_PATHS, date);
    }

    /**
     * Validate source/destination nodes
     *
     * @param sourceStation
     * @param destinationStation
     */
    private void validateNodes(String sourceStation, String destinationStation) {
        if (!labelMap.containsKey(sourceStation)) {
            throw new BadRequestException("source is invalid");
        }

        if (!labelMap.containsKey(destinationStation)) {
            throw new BadRequestException("destination is invalid");
        }
    }
}
