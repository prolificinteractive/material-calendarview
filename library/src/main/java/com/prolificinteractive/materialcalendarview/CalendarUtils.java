package com.prolificinteractive.materialcalendarview;

import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * Utilities for Calendar
 */
public class CalendarUtils {

    public static Calendar getInstance(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        copyDateTo(calendar, calendar);
        return calendar;
    }

    public static Calendar getInstance() {
        Calendar calendar = Calendar.getInstance();
        copyDateTo(calendar, calendar);
        return calendar;
    }

    public static void setToFirstDay(Calendar calendar) {
        int year = getYear(calendar);
        int month = getMonth(calendar);
        calendar.clear();
        calendar.set(year, month, 1);
        calendar.getTimeInMillis();
    }

    public static void copyDateTo(Calendar from, Calendar to) {
        to.clear();
        to.set(
                getYear(from),
                getMonth(from),
                getDay(from)
        );
        to.setTimeZone(from.getTimeZone());
        to.getTimeInMillis();
    }

    public static int getYear(Calendar calendar) {
        return calendar.get(YEAR);
    }

    public static int getMonth(Calendar calendar) {
        return calendar.get(MONTH);
    }

    public static int getDay(Calendar calendar) {
        return calendar.get(DATE);
    }

    public static int getDayOfWeek(Calendar calendar) {
        return calendar.get(DAY_OF_WEEK);
    }
}
