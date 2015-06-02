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

    /**
     * @return a new Calendar instance with the date set to the provided date. Time set to zero.
     */
    public static Calendar getInstance(Date date) {
        Calendar calendar = Calendar.getInstance();
        if(date != null) {
            calendar.setTime(date);
        }
        copyDateTo(calendar, calendar);
        return calendar;
    }

    /**
     * @return a new Calendar instance with the date set to today. Time set to zero.
     */
    public static Calendar getInstance() {
        Calendar calendar = Calendar.getInstance();
        copyDateTo(calendar, calendar);
        return calendar;
    }

    /**
     * Set the provided calendar to the first day of the month. Also clears all time information.
     */
    public static void setToFirstDay(Calendar calendar) {
        int year = getYear(calendar);
        int month = getMonth(calendar);
        calendar.clear();
        calendar.set(year, month, 1);
        calendar.getTimeInMillis();
    }

    /**
     * Copy <i>only</i> date information to a new calendar.
     */
    public static void copyDateTo(Calendar from, Calendar to) {
        int year = getYear(from);
        int month = getMonth(from);
        int day = getDay(from);
        to.clear();
        to.set(year, month, day);
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

	public static int getWeek(Calendar calendar) {
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public static int getDayOfWeek(Calendar calendar) {
        return calendar.get(DAY_OF_WEEK);
    }
}
