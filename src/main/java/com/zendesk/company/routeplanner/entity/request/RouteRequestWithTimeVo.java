package com.zendesk.company.routeplanner.entity.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

/**
 * Route request class with date parameter.
 */
@Getter
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@ToString(callSuper=true, includeFieldNames=true)
public class RouteRequestWithTimeVo extends RouteRequestVo {
    @NotEmpty(message = "date is mandatory")
    String date;
}
