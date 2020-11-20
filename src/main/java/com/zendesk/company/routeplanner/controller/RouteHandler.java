package com.zendesk.company.routeplanner.controller;

import com.zendesk.company.routeplanner.entity.graph.Node;
import com.zendesk.company.routeplanner.entity.request.RouteRequestVo;
import com.zendesk.company.routeplanner.entity.request.RouteRequestWithTimeVo;
import com.zendesk.company.routeplanner.entity.response.RouteResponseVo;
import com.zendesk.company.routeplanner.exception.NoValidPathsExistException;
import com.zendesk.company.routeplanner.graphoperations.GraphHolder;
import com.zendesk.company.routeplanner.service.GraphService;
import com.zendesk.company.routeplanner.service.RoutesService;
import com.zendesk.company.routeplanner.util.Consts;
import com.zendesk.company.routeplanner.util.TimeUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RouteHandler {
    private final Logger logger = LogManager.getLogger(RouteHandler.class);

    @Autowired
    RoutesService routesService;

    @Autowired
    GraphService graphService;

    @Operation(summary = "Find the shortest route (Only 1) between source station and destination station.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shortest route between source and destination is displayed", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "500", description = "Server error", content = {
                    @Content(mediaType = "application/json")
            })
    })
    @PostMapping(
            value = "/routes",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public RouteResponseVo getRoutesWithOutTimeRequest(@Valid @RequestBody RouteRequestVo requestVo) {
        logger.info("Request - {}", requestVo);
        String source = requestVo.getSource().toUpperCase();
        String destination = requestVo.getDestination().toUpperCase();

        // Clear graph data
        GraphHolder.getInstance().clearLoadedGraph();
        logger.info("Cleared graph.");

        // Load graph based on curr date
        graphService.setStartDate(new Date());
        Graph<Node, DefaultWeightedEdge> graph = graphService.generateGraph();
        logger.info("Graph loaded.");
        return routesService.findKShortestPaths(source, destination, graph);
    }

    @Operation(summary = "Find the shortest route (Only 3) between source station and destination station. " +
            "Calculate travel time. Top 3 paths are displayed based on travel time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Top 3 shortest routes between source and destination are displayed", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "500", description = "Server error", content = {
                    @Content(mediaType = "application/json")
            })
    })
    @PostMapping(
            value = "/routes/travel-time",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<RouteResponseVo> getRoutesWithTimeRequest(@Valid @RequestBody RouteRequestWithTimeVo requestVo) {
        logger.info("Request - {}", requestVo);
        String source = requestVo.getSource().toUpperCase();
        String destination = requestVo.getDestination().toUpperCase();
        Date date = TimeUtil.getDateFromString(requestVo.getDate(), Consts.REQUEST_DATE_FORMAT);

        // Clear graph data
        GraphHolder.getInstance().clearLoadedGraph();
        logger.info("Cleared graph.");

        // Load graph based on start date
        graphService.setStartDate(date);
        Graph<Node, DefaultWeightedEdge> graph = graphService.generateGraph();
        logger.info("Graph loaded.");

        List<RouteResponseVo> responseList = routesService.findKShortestPaths(source, destination, date, graph);
        if (responseList.isEmpty()) {
            throw new NoValidPathsExistException();
        }
        return responseList;
    }
}
