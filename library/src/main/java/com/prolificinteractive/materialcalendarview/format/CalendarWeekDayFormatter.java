package com.prolificinteractive.materialcalendarview.format;

import com.prolificinteractive.materialcalendarview.CalendarUtils;

import java.util.Calendar;
import java.util.Locale;

/**
 * Use a {@linkplain java.util.Calendar} to get week day labels.
 *
 * @see java.util.Calendar#getDisplayName(int, int, java.util.Locale)
 */
public class CalendarWeekDayFormatter implements WeekDayFormatter {

    private final Calendar calendar;

    /**
     * Format with a specific calendar
     *
     * @param calendar Calendar to retrieve formatting information from
     */
    public CalendarWeekDayFormatter(Calendar calendar) {
        this.calendar = calendar;
    }

    /**
     * Format with a default calendar
     */
    public CalendarWeekDayFormatter() {
        this(CalendarUtils.getInstance());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CharSequence format(int dayOfWeek) {
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
    }
}
