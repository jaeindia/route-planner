package com.zendesk.company.routeplanner.entity.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class RouteResponseVo {
    String source;

    String destination;

    Integer stations;

    List<String> route;

    List<String> steps;

    @JsonProperty("Time")
    Integer travelTime;

    @JsonIgnore
    boolean isValid;

    @JsonIgnore
    Date startTime;

    @JsonIgnore
    Date endTime;

    String start;

    String end;

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RouteResponseVo))
            return false;
        RouteResponseVo other = (RouteResponseVo) o;
        return this.route.size() == CollectionUtils.intersection(other.route, this.route).size();
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.route.toArray());
    }
}
