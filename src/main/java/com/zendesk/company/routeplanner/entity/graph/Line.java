package com.zendesk.company.routeplanner.entity.graph;

import lombok.Data;

/**
 * Line class implements a station line in the rail system.
 */
@Data
public class Line {
    private String code;

    private String transitLine;

    private Integer lineNumber;

    public Line(String code) {
        this.code = code;
        extractFromCode();
    }

    public Line(String transitLine, Integer lineNumber) {
        this.transitLine = transitLine;
        this.lineNumber = lineNumber;
    }

    private void extractFromCode() {
        String[] result = code.split("(?=\\d*$)",2);
        this.transitLine  = result[0];
        this.lineNumber = Integer.parseInt(result[1]);
    }
}
