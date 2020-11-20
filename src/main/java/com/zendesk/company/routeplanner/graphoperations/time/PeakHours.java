package com.zendesk.company.routeplanner.graphoperations.time;

import com.zendesk.company.routeplanner.util.Consts;
import com.zendesk.company.routeplanner.util.TimeUtil;

import java.util.Date;

public class PeakHours extends TimeOfDay {
    public PeakHours() {
        // Set time
        travelTimeMap.put(Consts.NS, 12);
        travelTimeMap.put(Consts.NE, 12);
        travelTimeMap.put(Consts.OTHERS, 10);
        changeLineDuration = 15;
        hoursMap.put(Consts.PEAK_HOURS_MORNING_START, TimeUtil.getDateFromString("06:00:00", Consts.HOUR_DATE_FORMAT));
        hoursMap.put(Consts.PEAK_HOURS_MORNING_END, TimeUtil.getDateFromString("09:00:00", Consts.HOUR_DATE_FORMAT));
        hoursMap.put(Consts.PEAK_HOURS_EVENING_START, TimeUtil.getDateFromString("18:00:00", Consts.HOUR_DATE_FORMAT));
        hoursMap.put(Consts.PEAK_HOURS_EVENING_END, TimeUtil.getDateFromString("21:00:00", Consts.HOUR_DATE_FORMAT));
    }

    @Override
    public boolean isLineActive(String code) {
        return true;
    }

    @Override
    public boolean canUseCalc(Date date, Date startDate) {
        // isWeekDay check
        if (!TimeUtil.isWeekDay(date)) {
            return false;
        }

        long currTime = TimeUtil.convertDateFormat(date, Consts.HOUR_DATE_FORMAT).getTime();
        // isPeakHours check
        return ((currTime >= hoursMap.get(Consts.PEAK_HOURS_MORNING_START).getTime()
                && currTime <= hoursMap.get(Consts.PEAK_HOURS_MORNING_END).getTime())
                ||
                (currTime >= hoursMap.get(Consts.PEAK_HOURS_EVENING_START).getTime()
                        && currTime <= hoursMap.get(Consts.PEAK_HOURS_EVENING_END).getTime()));
    }
}
