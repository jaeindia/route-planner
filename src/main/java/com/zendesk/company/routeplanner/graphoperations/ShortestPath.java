package com.zendesk.company.routeplanner.graphoperations;

import com.zendesk.company.routeplanner.entity.graph.Line;
import com.zendesk.company.routeplanner.entity.graph.Node;
import com.zendesk.company.routeplanner.entity.response.RouteResponseVo;
import com.zendesk.company.routeplanner.graphoperations.time.TravelTimeCalcContext;
import com.zendesk.company.routeplanner.util.Consts;
import com.zendesk.company.routeplanner.util.TimeUtil;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.text.StringSubstitutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.KShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.YenKShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.*;

public class ShortestPath {
    private final Logger logger = LogManager.getLogger(ShortestPath.class);
    private Map<String, String> edgeLabelCodeMap = GraphHolder.getInstance().getEdgeLabelCodeMap();
    private Map<String, Node> codeMap = GraphHolder.getInstance().getCodeMap();
    private KShortestPathAlgorithm<Node, DefaultWeightedEdge> kShortestPathAlgorithm;

    private Date startTime;
    private Date timeTracker;

    public ShortestPath() {
        startTime = new Date();
        timeTracker = new Date();
    }

    public RouteResponseVo findKShortestPaths(Node source, Node destination, int K, Graph<Node, DefaultWeightedEdge> graph) {
        kShortestPathAlgorithm = new YenKShortestPath<>(graph);
        List<GraphPath<Node, DefaultWeightedEdge>> paths = kShortestPathAlgorithm.getPaths(source, destination, K);
        RouteResponseVo routeResponseVo = RouteResponseVo.builder()
                .source(source.getLabel())
                .destination(destination.getLabel()).build();
        updateResponseVo(paths.get(0), routeResponseVo, false);
        return routeResponseVo;
    }

    public List<RouteResponseVo> findKShortestPaths(Node source, Node destination, int K, Date date, Graph<Node, DefaultWeightedEdge> graph) {
        kShortestPathAlgorithm = new YenKShortestPath<>(graph);
        List<GraphPath<Node, DefaultWeightedEdge>> paths = kShortestPathAlgorithm.getPaths(source, destination, K);
        logger.info("paths - {}", paths);
        List<RouteResponseVo> responseVos = new ArrayList<>();
        RouteResponseVo prevRouteResponseVo = null;
        for (GraphPath<Node, DefaultWeightedEdge> path : paths) {
            // Set timeTracker
            this.timeTracker = date;
            this.startTime = date;

            RouteResponseVo routeResponseVo = RouteResponseVo.builder()
                    .source(source.getLabel())
                    .destination(destination.getLabel())
                    .isValid(true)
                    .startTime(timeTracker)
                    .start(TimeUtil.convertDateToString(timeTracker, Consts.REQUEST_DATE_FORMAT))
                    .build();
            updateResponseVo(path, routeResponseVo, true);
            if (routeResponseVo.isValid())
                responseVos.add(routeResponseVo);

            logger.info("prevRouteResponseVo - {}", prevRouteResponseVo);
            logger.info("routeResponseVo - {}", routeResponseVo);

            // Remove duplicate paths
            if (Objects.nonNull(prevRouteResponseVo) && prevRouteResponseVo.equals(routeResponseVo)
                    && !responseVos.isEmpty()) {
                logger.info("Removing path - {}", responseVos.get(responseVos.size() - 1));
                responseVos.remove(responseVos.size() - 1);
            }

            prevRouteResponseVo = routeResponseVo;
        }

        // Sort paths by travel time
        responseVos.sort(Comparator.comparingInt(RouteResponseVo::getTravelTime));
        return responseVos;
    }

    private void updateResponseVo(GraphPath<Node, DefaultWeightedEdge> path, RouteResponseVo routeResponseVo, boolean calculateTravelTime) {
        Set<String> codePath = new LinkedHashSet<>();
        logger.info("Path - {}", path.getVertexList());
        logger.info("Edges - {}", path.getEdgeList().size());

        // Get route
        Node source = path.getVertexList().get(0);
        for (int i = 1; i < path.getVertexList().size(); i++) {
            Node destination = path.getVertexList().get(i);
            String edgeCodeMap = edgeLabelCodeMap.get(source.getLabel() + "-" + destination.getLabel());
            String sourceCode = edgeCodeMap.split("-", 2)[0];
            String destinationCode = edgeCodeMap.split("-", 2)[1];
            codePath.add(sourceCode);
            codePath.add(destinationCode);
            source = destination;
        }

        // Set route in responseVo
        List<String> route = new ArrayList<>();
        CollectionUtils.addAll(route, codePath);
        routeResponseVo.setRoute(route);

        // stations (Nodes = Edges + 1)
        routeResponseVo.setStations(path.getEdgeList().size() + 1);
        logger.info(codePath);

        // Generate steps and calculate travel time
        generateSteps(route, routeResponseVo, calculateTravelTime);
    }

    private void generateSteps(List<String> route, RouteResponseVo routeResponseVo, boolean calculateTravelTime) {
        List<String> steps = new ArrayList<>();

        Map<String, String> valuesMap = new HashMap<>();
        StringSubstitutor stepSub = new StringSubstitutor(valuesMap);
        String stepTemplate;

        Integer travelTime = 0;

        Line sourceLine = new Line(route.get(0));
        for (int i = 1; i < route.size(); i++) {
            Line destinationLine = new Line(route.get(i));
            boolean isSameLine = StringUtils.equals(sourceLine.getTransitLine(), destinationLine.getTransitLine());
            // Get step
            stepTemplate = generateStepFromTemplate(valuesMap, sourceLine, destinationLine, isSameLine);

            if (calculateTravelTime) {
                Integer currDuration = getCurrTravelDuration(sourceLine.getTransitLine(), destinationLine.getTransitLine(), isSameLine);
                if (Objects.isNull(currDuration)) {
                    calculateTravelTime = false;
                    routeResponseVo.setValid(false);
                    travelTime = null;
                } else {
                    travelTime += currDuration;
                    timeTracker = TimeUtil.addMinutes(timeTracker, currDuration);
                }
            }

            // Replace literals in the template string
            sourceLine = destinationLine;
            steps.add(stepSub.replace(stepTemplate));
        }

        // Set steps in response
        routeResponseVo.setSteps(steps);
        // Set travelTime
        if (calculateTravelTime && Objects.nonNull(travelTime) && travelTime > -1) {
            routeResponseVo.setTravelTime(travelTime);
            routeResponseVo.setEndTime(timeTracker);
            routeResponseVo.setEnd(TimeUtil.convertDateToString(timeTracker, Consts.REQUEST_DATE_FORMAT));
        }
    }

    private String generateStepFromTemplate(Map<String, String> valuesMap, Line sourceLine, Line destinationLine, boolean isSameLine) {
        String stepTemplate;
        if (isSameLine) {
            valuesMap.put(Consts.LINE, sourceLine.getTransitLine());
            valuesMap.put(Consts.SOURCE, codeMap.get(sourceLine.getCode()).getLabel());
            valuesMap.put(Consts.DESTINATION, codeMap.get(destinationLine.getCode()).getLabel());
            stepTemplate = Consts.TAKE_LINE;
        } else {
            valuesMap.put(Consts.SOURCE_LINE, sourceLine.getTransitLine());
            valuesMap.put(Consts.DESTINATION_LINE, destinationLine.getTransitLine());
            stepTemplate = Consts.CHANGE_LINE;
        }
        return stepTemplate;
    }

    private Integer getCurrTravelDuration(String sourceTransitLine, String destinationTransitLine, boolean isSameLine) {
        if (isSameLine)
            return getLineDuration(sourceTransitLine);
        else
            return getChangeLineDuration(destinationTransitLine);
    }

    private Integer getLineDuration(String code) {
        TravelTimeCalcContext context = new TravelTimeCalcContext(code, timeTracker, startTime);
        return context.getLineDuration();
    }

    private Integer getChangeLineDuration(String code) {
        TravelTimeCalcContext context = new TravelTimeCalcContext(code, timeTracker, startTime);
        return context.getChangeLineDuration();
    }
}
