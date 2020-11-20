package com.zendesk.company.routeplanner.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Consts {
    // CSV Import file name
    public static final String IMPORT_FILE_RESOURCE = "data/StationMap.csv";

    public static final String STATION_CODE = "Station Code";
    public static final String STATION_NAME = "Station Name";
    public static final String OPENING_DATE = "Opening Date";

    // Template literals
    public static final String TAKE_LINE = "Take ${line} line from ${source} to ${destination}";
    public static final String CHANGE_LINE = "Change from ${source_line} line to ${destination_line} line";

    // Template literals - keys
    public static final String LINE = "line";
    public static final String SOURCE = "source";
    public static final String DESTINATION = "destination";
    public static final String SOURCE_LINE = "source_line";
    public static final String DESTINATION_LINE = "destination_line";

    // MRT lines
    public static final String NS = "NS";
    public static final String NE = "NE";
    public static final String TE = "TE";
    public static final String DT = "DT";
    public static final String OTHERS = "OTHERS";

    // Time condition
    public static final String PEAK_HOURS_MORNING_START = "peak_hours_morning_start";
    public static final String PEAK_HOURS_MORNING_END = "peak_hours_morning_end";
    public static final String PEAK_HOURS_EVENING_START = "peak_hours_evening_start";
    public static final String PEAK_HOURS_EVENING_END = "peak_hours_evening_end";
    public static final String NIGHT_HOURS_START = "night_hours_start";
    public static final String NIGHT_HOURS_END = "night_hours_end";

    // Date format
    public static final String REQUEST_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm";
    public static final String HOUR_DATE_FORMAT = "HH:mm:ss";
    public static final String OPENING_DATE_FORMAT = "dd MMMM yyyy";
    public static final String DATE_DIFF_DAY_FORMAT = "MM/dd/yyyy";

    // TimeZone
    public static final String SGT = "Asia/Singapore";

    // Max K Paths
    public static final int MAX_PATHS = 3;

    // Error messages
    public static final String ERROR_IMPORTING_FROM_FILE = "Error importing stations from file.";
    public static final String CSV_READER_ERROR = "CSV reader error.";

    // ResponseEntity
    public static final String TIMESTAMP = "timestamp";
    public static final String STATUS = "status";
    public static final String MESSAGE = "message";
    public static final String PATH = "path";

    // Tests
    public static final String HTTP_LOCALHOST = "http://localhost:";
    public static final String ROUTES = "/api/routes";
    public static final String ROUTES_WITH_TIME = "/api/routes/travel-time";
    public static final String X_COM_PERSIST = "X-COM-PERSIST";
    public static final String TRUE = "true";

    public static final String TIME_STAMP_FORMAT = "yyyy-MM-dd hh:mm:ss";
}
