package com.zendesk.company.routeplanner.entity.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@ToString
@Getter
public class CustomErrorResponse {
    @JsonProperty("timestamp")
    private Date timestamp;

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("message")
    private Set<String> message;

    @JsonProperty("path")
    private String path;
}
