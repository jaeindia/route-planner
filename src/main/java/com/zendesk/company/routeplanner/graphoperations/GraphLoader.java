package com.zendesk.company.routeplanner.graphoperations;

import com.zendesk.company.routeplanner.entity.graph.Edge;
import com.zendesk.company.routeplanner.entity.graph.Line;
import com.zendesk.company.routeplanner.entity.graph.Node;
import com.zendesk.company.routeplanner.exception.ServerErrorException;
import com.zendesk.company.routeplanner.util.Consts;
import com.zendesk.company.routeplanner.util.TimeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

public class GraphLoader {
    private final Logger logger = LogManager.getLogger(GraphLoader.class);

    private Map<String, Edge> edgeMap = GraphHolder.getInstance().getEdgeMap();
    private Map<String, Node> codeMap = GraphHolder.getInstance().getCodeMap();
    private Map<String, Node> labelMap = GraphHolder.getInstance().getLabelMap();
    private Map<String, String> edgeLabelCodeMap = GraphHolder.getInstance().getEdgeLabelCodeMap();
    private Map<String, Set<String>> lineStationsMap = GraphHolder.getInstance().getLineStationsMap();
    private Map<String, List<String>> lineStationsSortedMap = GraphHolder.getInstance().getLineStationsSortedMap();

    public Reader getReader() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(Consts.IMPORT_FILE_RESOURCE);
        if (Objects.isNull(inputStream)) {
            throw new ServerErrorException(Consts.ERROR_IMPORTING_FROM_FILE);
        }
        return new InputStreamReader(inputStream);
    }

    public void readAll(Reader reader, Date startDate) throws IOException {
        Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(reader);

        for (CSVRecord record : records) {
            // Read via CSV header map
            String codeValue = record.get(Consts.STATION_CODE);
            String label = record.get(Consts.STATION_NAME);
            Date openingDate = TimeUtil.getDateFromString(record.get(Consts.OPENING_DATE), Consts.OPENING_DATE_FORMAT);

            if (!canAddNode(openingDate, startDate)) continue;

            // {label -> Node} mapping
            if (!labelMap.containsKey(label.toUpperCase()))
                labelMap.put(label.toUpperCase(), new Node(label));

            // Update node properties
            Node node = labelMap.get(label.toUpperCase());
            node.addCode(codeValue);
            node.setOpeningDate(openingDate);
            codeMap.put(codeValue, node);

            // Update {line -> stations} map
            Line line = new Line(codeValue);
            if (!lineStationsMap.containsKey(line.getTransitLine()))
                lineStationsMap.put(line.getTransitLine(), new HashSet<>());
            lineStationsMap.get(line.getTransitLine()).add(codeValue);
        }

        // Generate {line -> stations in sequence} map
        createLineStationsSortedMap();
    }

    private boolean canAddNode(Date openingDate, Date currDate) {
        Calendar startDate = TimeUtil.getCalendarInstance(openingDate);
        Calendar now = TimeUtil.getCalendarInstance(currDate);
        return now.after(startDate);
    }

    // Generate {line -> stations in sequence} map
    private void createLineStationsSortedMap() {
        for (Map.Entry<String, Set<String>> entry : lineStationsMap.entrySet()) {
            String line = entry.getKey();
            Set<String> stationsSet = entry.getValue();
            List<String> stationsList = new ArrayList<>();
            CollectionUtils.addAll(stationsList, stationsSet);
            // Sort stations
            stationsList.sort((s1, s2) -> {
                Line line1 = new Line(s1);
                Line line2 = new Line(s2);
                return line1.getLineNumber() - line2.getLineNumber();
            });
            lineStationsSortedMap.put(line, stationsList);
        }

        logger.info("lineStationsSortedMap - {}", lineStationsSortedMap);
    }

    public void generateEdges() {
        for (Map.Entry<String, Node> entry : codeMap.entrySet()) {
            String sourceCodeValue = entry.getKey();
            Line line = new Line(sourceCodeValue);
            List<String> stationsList = lineStationsSortedMap.get(line.getTransitLine());
            int currIndex = stationsList.indexOf(line.getCode());

            String sinkCodeValue;
            // Next Node
            if (currIndex + 1 < stationsList.size()) {
                sinkCodeValue = stationsList.get(currIndex + 1);
                addDirectedEdgeToMap(sourceCodeValue, sinkCodeValue);
            }
            // Previous Node
            if (currIndex - 1 > -1) {
                sinkCodeValue = stationsList.get(currIndex - 1);
                addDirectedEdgeToMap(sourceCodeValue, sinkCodeValue);
            }
        }
    }

    private void addDirectedEdgeToMap(String sourceCodeValue, String sinkCodeValue) {
        if (codeMap.containsKey(sinkCodeValue)) {
            Node source = codeMap.get(sourceCodeValue);
            Node sink = codeMap.get(sinkCodeValue);
            // source -> sink edge
            addEdgeToMap(source, sink, sourceCodeValue, sinkCodeValue);
            // sink -> source edge
            addEdgeToMap(sink, source, sinkCodeValue, sourceCodeValue);
        }
    }

    private void addEdgeToMap(Node source, Node sink, String sourceCodeValue, String sinkCodeValue) {
        Edge edge = new Edge(source, sink);
        String edgeCodeMap = sourceCodeValue + "-" + sinkCodeValue;
        String edgeLabelMap = source.getLabel() + "-" + sink.getLabel();
        if (!edgeMap.containsKey(edgeCodeMap))
            edgeMap.put(edgeCodeMap, edge);
        if (!edgeLabelCodeMap.containsKey(edgeLabelMap))
            edgeLabelCodeMap.put(edgeLabelMap, edgeCodeMap);
    }

    public Graph<Node, DefaultWeightedEdge> generateGraph() {
        Graph<Node, DefaultWeightedEdge> graph = new DirectedWeightedMultigraph<>(DefaultWeightedEdge.class);

        // Add nodes to the Graph
        for (Map.Entry<String, Node> entry : labelMap.entrySet()) {
            Node node = entry.getValue();
            graph.addVertex(node);
        }

        // Add edges to the Graph
        for (Map.Entry<String, Edge> entry : edgeMap.entrySet()) {
            Edge edge = entry.getValue();
            DefaultWeightedEdge graphEdge = graph.addEdge(edge.getSource(), edge.getSink());
            graph.setEdgeWeight(graphEdge, edge.getWeight());
        }

        return graph;
    }
}
