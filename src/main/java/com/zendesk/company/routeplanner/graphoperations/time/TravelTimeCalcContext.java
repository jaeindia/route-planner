package com.zendesk.company.routeplanner.graphoperations.time;

import java.util.Date;

public class TravelTimeCalcContext {
    private TimeOfDay peakHours;

    private TimeOfDay nightHours;

    private TimeOfDay nonPeakHours;

    private TimeOfDay timeOfDay;

    private String code;

    private Date currDate;

    private Date startDate;

    public TravelTimeCalcContext(String code, Date currDate, Date startDate) {
        this.code = code;
        this.currDate = currDate;
        this.startDate = startDate;
        peakHours = new PeakHours();
        nightHours = new NightHours();
        nonPeakHours = new NonPeakHours();
    }

    private void setTimeOfDay() {
        if (peakHours.canUseCalc(currDate, startDate)) {
            timeOfDay = peakHours;
        } else if (nightHours.canUseCalc(currDate, startDate)) {
            timeOfDay = nightHours;
        } else {
            timeOfDay = nonPeakHours;
        }
    }

    public Integer getLineDuration() {
        setTimeOfDay();
        if (!timeOfDay.isLineActive(code)) {
            return null;
        }

        return timeOfDay.getLineDuration(code);
    }

    public Integer getChangeLineDuration() {
        setTimeOfDay();
        if (!timeOfDay.isLineActive(code)) {
            return null;
        }

        return timeOfDay.getChangeLineDuration();
    }
}