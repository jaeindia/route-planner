package com.zendesk.company.routeplanner;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zendesk.company.routeplanner.entity.request.RouteRequestVo;
import com.zendesk.company.routeplanner.entity.request.RouteRequestWithTimeVo;
import com.zendesk.company.routeplanner.entity.response.CustomErrorResponse;
import com.zendesk.company.routeplanner.entity.response.RouteResponseVo;
import com.zendesk.company.routeplanner.constants.Consts;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Integration test class to check the behaviour of the api - /api/routes/travel-time
 */
public class RouteRequestsWithTimeIT extends AbstractTestIT {
    private final Logger logger = LogManager.getLogger(RouteRequestsWithTimeIT.class);

    @Test
    public void destinationNullRequest() throws Exception {
        URI uri = getUri();
        RouteRequestWithTimeVo routeRequestVo = RouteRequestWithTimeVo.builder()
                .source("Boon Lay")
                .destination(null)
                .date("2005-01-31T16:00")
                .build();

        HttpEntity<RouteRequestVo> request = new HttpEntity<>(routeRequestVo, httpHeaders);
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
        logger.info("result - {}", result.getBody());

        CustomErrorResponse errorResponse = mapFromJson(result.getBody(), CustomErrorResponse.class);
        logger.info("responseVo - {}", errorResponse);

        Assert.assertEquals(400, result.getStatusCodeValue());
        Assert.assertTrue(errorResponse.getMessage().contains("destination is mandatory"));
    }

    @Test
    public void sourceInvalidRequest() throws Exception {
        URI uri = getUri();
        RouteRequestWithTimeVo routeRequestVo = RouteRequestWithTimeVo.builder()
                .source(" ")
                .destination("Little India")
                .date("2005-01-31T16:00")
                .build();

        HttpEntity<RouteRequestVo> request = new HttpEntity<>(routeRequestVo, httpHeaders);
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
        logger.info("result - {}", result.getBody());

        CustomErrorResponse errorResponse = mapFromJson(result.getBody(), CustomErrorResponse.class);
        logger.info("responseVo - {}", errorResponse);

        Assert.assertEquals(400, result.getStatusCodeValue());
        Assert.assertTrue(errorResponse.getMessage().contains("source is invalid"));
    }

    @Test
    public void destinationDateNullRequest() throws Exception {
        URI uri = getUri();
        RouteRequestWithTimeVo routeRequestVo = RouteRequestWithTimeVo.builder()
                .source("Boon Lay")
                .destination(null)
                .date("")
                .build();

        HttpEntity<RouteRequestVo> request = new HttpEntity<>(routeRequestVo, httpHeaders);
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
        logger.info("result - {}", result.getBody());

        CustomErrorResponse errorResponse = mapFromJson(result.getBody(), CustomErrorResponse.class);
        logger.info("responseVo - {}", errorResponse);

        Assert.assertEquals(400, result.getStatusCodeValue());
        Set<String> message = new HashSet<>(Arrays.asList("date is mandatory", "destination is mandatory"));
        Assert.assertEquals(message.size(), CollectionUtils.intersection(message, errorResponse.getMessage()).size());
    }

    @Test
    public void dateInvalidRequest() throws Exception {
        URI uri = getUri();
        RouteRequestWithTimeVo routeRequestVo = RouteRequestWithTimeVo.builder()
                .source("Boon Lay")
                .destination("Little India")
                .date("2005-01-31T16")
                .build();

        HttpEntity<RouteRequestVo> request = new HttpEntity<>(routeRequestVo, httpHeaders);
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
        logger.info("result - {}", result.getBody());

        CustomErrorResponse errorResponse = mapFromJson(result.getBody(), CustomErrorResponse.class);
        logger.info("responseVo - {}", errorResponse);

        Assert.assertEquals(400, result.getStatusCodeValue());
        Assert.assertTrue(errorResponse.getMessage().contains("date format is invalid (YYYY-MM-DDThh:mm)"));
    }

    @Test
    public void sourceDestinationValidRequest() throws Exception {
        URI uri = getUri();
        RouteRequestWithTimeVo routeRequestVo = RouteRequestWithTimeVo.builder()
                .source("Clementi")
                .destination("one-north")
                .date("2020-01-31T16:00")
                .build();

        HttpEntity<RouteRequestVo> request = new HttpEntity<>(routeRequestVo, httpHeaders);
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
        logger.info("result - {}", result.getBody());

        List<RouteResponseVo> responseVos = mapListResponseFromJson(result.getBody());
        logger.info("responseVos - {}", responseVos);

        List<RouteResponseVo> expectedResponseVos = new ArrayList<>();
        // Route 1
        RouteResponseVo expectedResponseVo = RouteResponseVo.builder()
                .source("Clementi")
                .destination("one-north")
                .stations(4)
                .route(Arrays.asList("EW23", "EW22", "EW21", "CC22", "CC23"))
                .start("2020-01-31T16:00")
                .end("2020-01-31T16:40")
                .travelTime(40)
                .build();
        expectedResponseVos.add(expectedResponseVo);
        // Route 2
        expectedResponseVo = RouteResponseVo.builder()
                .source("Clementi")
                .destination("one-north")
                .stations(15)
                .route(Arrays.asList("EW23", "EW22", "EW21", "EW20", "EW19", "EW18", "EW17", "EW16", "NE3", "NE1", "CC29", "CC28",
                        "CC27", "CC26", "CC25", "CC24", "CC23"))
                .start("2020-01-31T16:00")
                .end("2020-01-31T18:40")
                .travelTime(160)
                .build();
        expectedResponseVos.add(expectedResponseVo);
        // Route 3
        expectedResponseVo = RouteResponseVo.builder()
                .source("Clementi")
                .destination("one-north")
                .stations(20)
                .route(Arrays.asList("EW23", "EW22", "EW21", "CC22", "CC21", "CC20", "CC19", "DT9", "DT10", "DT11", "DT12", "NE7",
                        "NE6", "NE5", "NE4", "NE3", "NE1", "CC29", "CC28", "CC27", "CC26", "CC25", "CC24", "CC23"))
                .start("2020-01-31T16:00")
                .end("2020-01-31T19:55")
                .travelTime(235)
                .build();
        expectedResponseVos.add(expectedResponseVo);

        Assert.assertEquals(200, result.getStatusCodeValue());
        int i = 0;
        for (RouteResponseVo responseVo : responseVos) {
            // Skip steps check
            responseVo.setSteps(null);
            logger.info("responseVo - {}", responseVo);

            String actual = mapToJson(responseVo);
            String expected = mapToJson(expectedResponseVos.get(i++));
            Assert.assertEquals(expected, actual);
        }
    }

    @Test
    public void noValidRoutesAvailableRequest() throws Exception {
        URI uri = getUri();
        RouteRequestWithTimeVo routeRequestVo = RouteRequestWithTimeVo.builder()
                .source("Clementi")
                .destination("Hillview")
                .date("2022-01-31T22:40")
                .build();

        HttpEntity<RouteRequestVo> request = new HttpEntity<>(routeRequestVo, httpHeaders);
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
        logger.info("result - {}", result.getBody());

        CustomErrorResponse errorResponse = mapFromJson(result.getBody(), CustomErrorResponse.class);
        logger.info("responseVo - {}", errorResponse);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertTrue(errorResponse.getMessage().contains("No valid paths exist."));
    }

    @Test
    public void nightHoursTravelRequest() throws Exception {
        URI uri = getUri();
        RouteRequestWithTimeVo routeRequestVo = RouteRequestWithTimeVo.builder()
                .source("Clementi")
                .destination("one-north")
                .date("2022-01-31T23:40")
                .build();

        HttpEntity<RouteRequestVo> request = new HttpEntity<>(routeRequestVo, httpHeaders);
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
        logger.info("result - {}", result.getBody());

        List<RouteResponseVo> responseVos = mapListResponseFromJson(result.getBody());
        logger.info("responseVos - {}", responseVos);

        List<RouteResponseVo> expectedResponseVos = new ArrayList<>();
        // Route 1
        RouteResponseVo expectedResponseVo = RouteResponseVo.builder()
                .source("Clementi")
                .destination("one-north")
                .stations(4)
                .route(Arrays.asList("EW23", "EW22", "EW21", "CC22", "CC23"))
                .start("2022-01-31T23:40")
                .end("2022-02-01T00:20")
                .travelTime(40)
                .build();
        expectedResponseVos.add(expectedResponseVo);
        // Route 2
        expectedResponseVo = RouteResponseVo.builder()
                .source("Clementi")
                .destination("one-north")
                .stations(15)
                .route(Arrays.asList("EW23", "EW22", "EW21", "EW20", "EW19", "EW18", "EW17", "EW16", "NE3", "NE1", "CC29", "CC28",
                        "CC27", "CC26", "CC25", "CC24", "CC23"))
                .start("2022-01-31T23:40")
                .end("2022-02-01T02:20")
                .travelTime(160)
                .build();
        expectedResponseVos.add(expectedResponseVo);

        Assert.assertEquals(200, result.getStatusCodeValue());
        int i = 0;
        for (RouteResponseVo responseVo : responseVos) {
            // Skip steps check
            responseVo.setSteps(null);
            logger.info("responseVo - {}", responseVo);

            String actual = mapToJson(responseVo);
            String expected = mapToJson(expectedResponseVos.get(i++));
            Assert.assertEquals(expected, actual);
        }
    }

    @Test
    public void stationNotOpenedYetRequest() throws Exception {
        URI uri = getUri();
        // one-north station (destination) Opening Date - 8 October 2011
        RouteRequestWithTimeVo routeRequestVo = RouteRequestWithTimeVo.builder()
                .source("Clementi")
                .destination("one-north")
                .date("2005-01-31T16:00")
                .build();

        HttpEntity<RouteRequestVo> request = new HttpEntity<>(routeRequestVo, httpHeaders);
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
        logger.info("result - {}", result.getBody());

        CustomErrorResponse errorResponse = mapFromJson(result.getBody(), CustomErrorResponse.class);
        logger.info("responseVo - {}", errorResponse);

        Assert.assertEquals(400, result.getStatusCodeValue());
        Assert.assertTrue(errorResponse.getMessage().contains("destination is invalid"));
    }

    private List<RouteResponseVo> mapListResponseFromJson(String json)
            throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper.readValue(json, new TypeReference<List<RouteResponseVo>>() {
                }
        );
    }

    @NotNull
    private URI getUri() throws URISyntaxException {
        final String baseUrl = Consts.HTTP_LOCALHOST + randomServerPort + Consts.ROUTES_WITH_TIME;
        URI uri = new URI(baseUrl);
        return uri;
    }
}
