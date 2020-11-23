package com.zendesk.company.routeplanner.service.graphoperations.time;

import com.zendesk.company.routeplanner.constants.Consts;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
/**
 * Class to determine the time of day.
 */
public abstract class TimeOfDay {
    // Travel duration (line)
    Map<String, Integer> travelTimeMap = new HashMap<>();

    // Peak/Night hours
    Map<String, Date> hoursMap = new HashMap<>();

    Integer changeLineDuration;

    public abstract boolean isLineActive(String code);

    public abstract boolean canUseCalc(Date currDate, Date startDate);

    public Integer getLineDuration(String code) {
        if (travelTimeMap.containsKey(code)) {
            return travelTimeMap.get(code);
        }
        return travelTimeMap.get(Consts.OTHERS);
    }

    public Integer getChangeLineDuration() {
        return changeLineDuration;
    }
}
