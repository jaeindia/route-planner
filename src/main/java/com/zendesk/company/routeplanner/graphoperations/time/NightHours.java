package com.zendesk.company.routeplanner.graphoperations.time;

import com.zendesk.company.routeplanner.util.Consts;
import com.zendesk.company.routeplanner.util.TimeUtil;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Class to check if the time of day is night hours.
 */
public class NightHours extends TimeOfDay {
    public NightHours() {
        // Set time
        travelTimeMap.put(Consts.TE, 8);
        travelTimeMap.put(Consts.OTHERS, 10);
        changeLineDuration = 10;
        hoursMap.put(Consts.NIGHT_HOURS_START, TimeUtil.getDateFromString("22:00:00", Consts.HOUR_DATE_FORMAT));
        // Next day morning
        hoursMap.put(Consts.NIGHT_HOURS_END, TimeUtil.addDays(
                TimeUtil.getDateFromString("06:00:00", Consts.HOUR_DATE_FORMAT),
                1
        ));
    }

    @Override
    public boolean isLineActive(String code) {
        Set<String> inactiveLines = new HashSet<>(Arrays.asList("DT", "CG", "CE"));
        return !inactiveLines.contains(code);
    }

    @Override
    public boolean canUseCalc(Date date, Date startDate) {
        Date currDate = date;
        // Add 1 day if past midnight
        if (TimeUtil.getDateDiffInDays(startDate, currDate) > 0) {
            currDate = TimeUtil.addDays(TimeUtil.convertDateFormat(currDate, Consts.HOUR_DATE_FORMAT), 1);
        }

        // isNightHours check
        long currTime = currDate.getTime();
        return currTime >= hoursMap.get(Consts.NIGHT_HOURS_START).getTime()
                && currTime <= hoursMap.get(Consts.NIGHT_HOURS_END).getTime();
    }
}