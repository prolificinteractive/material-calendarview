package com.prolificinteractive.materialcalendarview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
     * @param date {@linkplain Date} to pull date information from
     * @return a new Calendar instance with the date set to the provided date. Time set to zero.
     */
    public static Calendar getInstance(@Nullable Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        copyDateTo(calendar, calendar);
        return calendar;
    }

    /**
     * @return a new Calendar instance with the date set to today. Time set to zero.
     */
    @NonNull
    public static Calendar getInstance() {
        Calendar calendar = Calendar.getInstance();
        copyDateTo(calendar, calendar);
        return calendar;
    }

    /**
     * Set the provided calendar to the first day of the month. Also clears all time information.
     *
     * @param calendar {@linkplain Calendar} to modify to be at the first fay of the month
     */
    public static void setToFirstDay(Calendar calendar) {
        int year = getYear(calendar);
        int month = getMonth(calendar);
        calendar.clear();
        calendar.set(year, month, 1);
    }

    /**
     * Copy <i>only</i> date information to a new calendar.
     *
     * @param from calendar to copy from
     * @param to   calendar to copy to
     */
    public static void copyDateTo(Calendar from, Calendar to) {
        int year = getYear(from);
        int month = getMonth(from);
        int day = getDay(from);
        to.clear();
        to.set(year, month, day);
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
