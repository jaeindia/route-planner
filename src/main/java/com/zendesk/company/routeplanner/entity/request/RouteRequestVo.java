package com.zendesk.company.routeplanner.entity.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

@Getter
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@ToString
public class RouteRequestVo {
    @NotEmpty(message = "source is mandatory")
    String source;

    @NotEmpty(message = "destination is mandatory")
    String destination;
}
