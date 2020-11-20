package com.zendesk.company.routeplanner.util;

import com.zendesk.company.routeplanner.exception.BadRequestException;
import com.zendesk.company.routeplanner.exception.ServerErrorException;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Utility class for date operations.
 */
public class TimeUtil {
    public static Date getDateFromString(String dateInString, String dateFormat) {
        if (StringUtils.isEmpty(dateInString)) {
            throw new BadRequestException("date is invalid");
        }

        DateFormat formatter = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getTimeZone(Consts.SGT));
        try {
            return formatter.parse(dateInString);
        } catch (ParseException e) {
            throw new BadRequestException("date format is invalid (YYYY-MM-DDThh:mm)");
        }
    }

    public static Calendar getCalendarInstance(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static boolean isWeekDay(Date date) {
        Calendar calendar = getCalendarInstance(date);
        return !(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
    }

    public static Date addMinutes(Date date, int min) {
        Calendar calendar = getCalendarInstance(date);
        calendar.add(Calendar.MINUTE, min);
        return calendar.getTime();
    }

    public static Date addDays(Date date, int days) {
        Calendar calendar = getCalendarInstance(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    public static String convertDateToString(Date date, String dateFormat) {
        Calendar calendar = getCalendarInstance(date);
        DateFormat formatter = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getTimeZone(Consts.SGT));
        return formatter.format(calendar.getTime());
    }

    public static Date convertDateFormat(Date date, String dateFormat) {
        DateFormat formatter = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getTimeZone(Consts.SGT));
        try {
            return formatter.parse(formatter.format(date));
        } catch (ParseException e) {
            throw new ServerErrorException("date format is invalid");
        }
    }

    public static long getDateDiffInDays(Date start, Date end) {
        long diffInMillis = Math.abs(convertDateFormat(end, Consts.DATE_DIFF_DAY_FORMAT).getTime() -
                convertDateFormat(start, Consts.DATE_DIFF_DAY_FORMAT).getTime());
        return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }
}
