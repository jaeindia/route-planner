package com.zendesk.company.routeplanner.service;

import com.zendesk.company.routeplanner.entity.graph.Node;
import com.zendesk.company.routeplanner.exception.ServerErrorException;
import com.zendesk.company.routeplanner.graphoperations.GraphHolder;
import com.zendesk.company.routeplanner.graphoperations.GraphLoader;
import com.zendesk.company.routeplanner.util.Consts;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.util.Date;

@Service
public class GraphService {
    private final Logger logger = LogManager.getLogger(GraphService.class);
    private GraphLoader graphLoader;
    private Graph<Node, DefaultWeightedEdge> graph;
    @Setter
    private Date startDate;

    public GraphService() {
        graphLoader = new GraphLoader();
        graph = GraphHolder.getInstance().getGraph();
        startDate = new Date();
    }


    /**
     * Generate Staions graph from the import file.
     */
    public void generateMap() {
        Reader reader = graphLoader.getReader();

        try {
            graphLoader.readAll(reader, startDate);
        } catch (IOException e) {
            throw new ServerErrorException(Consts.CSV_READER_ERROR);
        }

        // Generate edges
        graphLoader.generateEdges();
        logger.info("edgeMap - {}", GraphHolder.getInstance().getEdgeMap());
        logger.info("edges - {}", GraphHolder.getInstance().getEdgeMap().values().size());

        // Load Nodes and Edges in the graph
        graphLoader.generateGraph();
        logger.info("Graph loaded.");
    }
}
