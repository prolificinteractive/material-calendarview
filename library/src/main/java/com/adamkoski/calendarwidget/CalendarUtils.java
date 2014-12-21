package com.adamkoski.calendarwidget;

import java.util.Calendar;

import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * Calendar Utilities
 */
public class CalendarUtils {

    public static Calendar copy(Calendar from) {
        if(from == null) {
            return null;
        }
        Calendar to = Calendar.getInstance();
        to.clear();
        to.set(
                from.get(YEAR),
                from.get(MONTH),
                from.get(DATE)
        );
        to.setTimeZone(from.getTimeZone());
        to.getTimeInMillis();
        return to;
    }

    public static void copyDateTo(Calendar from, Calendar to) {
        to.clear();
        to.set(
                from.get(YEAR),
                from.get(MONTH),
                from.get(DATE)
        );
        to.setTimeZone(from.getTimeZone());
        to.getTimeInMillis();
    }

    public static void setToFirstDay(Calendar calendar) {
        int year = calendar.get(YEAR);
        int month = calendar.get(MONTH);
        calendar.clear();
        calendar.set(year, month, 1);
        calendar.getTimeInMillis();
    }
}
