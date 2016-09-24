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
        // recompute all fields of the calendar based on current date
        // See "Getting and Setting Calendar Field Values"
        // in https://developer.android.com/reference/java/util/Calendar.html
        calendar.get(Calendar.DAY_OF_WEEK);  // Any fields to get is OK to recompute all fields in the calendar.
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
