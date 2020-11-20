package com.zendesk.company.routeplanner.graphoperations.time;

import com.zendesk.company.routeplanner.util.Consts;

import java.util.Date;

public class NonPeakHours extends TimeOfDay{
    public NonPeakHours() {
        // Set time
        travelTimeMap.put(Consts.TE, 8);
        travelTimeMap.put(Consts.DT, 8);
        travelTimeMap.put(Consts.OTHERS, 10);
        changeLineDuration = 10;
    }

    @Override
    public boolean isLineActive(String code) {
        return true;
    }

    @Override
    public boolean canUseCalc(Date date, Date startDate) {
        return true;
    }
}