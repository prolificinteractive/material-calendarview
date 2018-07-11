package com.prolificinteractive.materialcalendarview;

import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Locale;

import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * Utilities for Calendar
 */
public class CalendarUtils {


    /**
     * @return a new Calendar instance with the date set to today.
     */
    @NonNull
    public static Calendar getInstance() {
        return Calendar.getInstance(Locale.getDefault());
    }

    public static int getYear(final Calendar calendar) {
        return calendar.get(YEAR);
    }

    public static int getMonth(final Calendar calendar) {
        return calendar.get(MONTH);
    }

    public static int getDay(final Calendar calendar) {
        return calendar.get(DATE);
    }

    public static int getDayOfWeek(final Calendar calendar) {
        return calendar.get(DAY_OF_WEEK);
    }
}
