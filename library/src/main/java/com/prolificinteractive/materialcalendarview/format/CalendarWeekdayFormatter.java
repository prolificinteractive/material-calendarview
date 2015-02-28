package com.prolificinteractive.materialcalendarview.format;

import java.util.Calendar;
import java.util.Locale;

/**
 *
 */
public class CalendarWeekDayFormatter implements WeekDayFormatter {

    private final Calendar calendar;

    public CalendarWeekDayFormatter(Calendar calendar) {
        this.calendar = calendar;
    }

    @Override
    public CharSequence format(int dayOfWeek) {
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
    }
}
