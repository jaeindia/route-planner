package com.zendesk.company.routeplanner;

import com.zendesk.company.routeplanner.entity.request.RouteRequestVo;
import com.zendesk.company.routeplanner.entity.response.CustomErrorResponse;
import com.zendesk.company.routeplanner.entity.response.RouteResponseVo;
import com.zendesk.company.routeplanner.constants.Consts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

/**
 * Integration test class to check the behaviour of the api - /api/routes
 */
public class RouteRequestsWithoutTimeIT extends AbstractTestIT {
    private final Logger logger = LogManager.getLogger(RouteRequestsWithoutTimeIT.class);

    @Test
    public void sourceNullRequest() throws Exception {
        URI uri = getUri();
        RouteRequestVo routeRequestVo = RouteRequestVo.builder()
                .source(null)
                .destination("Little India")
                .build();

        HttpEntity<RouteRequestVo> request = new HttpEntity<>(routeRequestVo, httpHeaders);
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
        logger.info("result - {}", result.getBody());

        CustomErrorResponse errorResponse = mapFromJson(result.getBody(), CustomErrorResponse.class);
        logger.info("responseVo - {}", errorResponse);

        Assert.assertEquals(400, result.getStatusCodeValue());
        Assert.assertTrue(errorResponse.getMessage().contains("source is mandatory"));
    }

    @Test
    public void sourceInvalidRequest() throws Exception {
        URI uri = getUri();
        RouteRequestVo routeRequestVo = RouteRequestVo.builder()
                .source("Boon")
                .destination("Little India")
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
    public void sourceDestinationValidRequest() throws Exception {
        URI uri = getUri();
        RouteRequestVo routeRequestVo = RouteRequestVo.builder()
                .source("Boon Lay")
                .destination("Little India")
                .build();

        HttpEntity<RouteRequestVo> request = new HttpEntity<>(routeRequestVo, httpHeaders);
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
        logger.info("result - {}", result.getBody());

        RouteResponseVo responseVo = mapFromJson(result.getBody(), RouteResponseVo.class);
        logger.info("responseVo - {}", responseVo);

        RouteResponseVo expectedResponseVo = RouteResponseVo.builder()
                .source("Boon Lay")
                .destination("Little India")
                .stations(13)
                .route(Arrays.asList("EW27", "EW26", "EW25", "EW24", "EW23", "EW22", "EW21", "CC22", "CC21", "CC20", "CC19", "DT9", "DT10", "DT11", "DT12"))
                .steps(Arrays.asList("Take EW line from Boon Lay to Lakeside",
                        "Take EW line from Lakeside to Chinese Garden",
                        "Take EW line from Chinese Garden to Jurong East",
                        "Take EW line from Jurong East to Clementi",
                        "Take EW line from Clementi to Dover",
                        "Take EW line from Dover to Buona Vista",
                        "Change from EW line to CC line",
                        "Take CC line from Buona Vista to Holland Village",
                        "Take CC line from Holland Village to Farrer Road",
                        "Take CC line from Farrer Road to Botanic Gardens",
                        "Change from CC line to DT line",
                        "Take DT line from Botanic Gardens to Stevens",
                        "Take DT line from Stevens to Newton",
                        "Take DT line from Newton to Little India"))
                .build();
        String actual = mapToJson(responseVo);
        String expected = mapToJson(expectedResponseVo);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void sourceDestinationValidRequest_skipStepsCheck() throws Exception {
        URI uri = getUri();
        RouteRequestVo routeRequestVo = RouteRequestVo.builder()
                .source("Clementi")
                .destination("one-north")
                .build();

        HttpEntity<RouteRequestVo> request = new HttpEntity<>(routeRequestVo, httpHeaders);
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
        logger.info("result - {}", result.getBody());

        RouteResponseVo responseVo = mapFromJson(result.getBody(), RouteResponseVo.class);
        logger.info("responseVo - {}", responseVo);
        // Skip steps check
        responseVo.setSteps(null);

        RouteResponseVo expectedResponseVo = RouteResponseVo.builder()
                .source("Clementi")
                .destination("one-north")
                .stations(4)
                .route(Arrays.asList("EW23", "EW22", "EW21", "CC22", "CC23"))
                .build();
        String actual = mapToJson(responseVo);
        String expected = mapToJson(expectedResponseVo);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(expected, actual);
    }

    @NotNull
    private URI getUri() throws URISyntaxException {
        final String baseUrl = Consts.HTTP_LOCALHOST + randomServerPort + Consts.ROUTES;
        URI uri = new URI(baseUrl);
        return uri;
    }
}
