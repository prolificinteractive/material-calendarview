package com.prolificinteractive.materialcalendarview.format;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.SimpleDateFormat;

/**
 * Supply labels for a given day. Default implementation is to format using a {@linkplain SimpleDateFormat}
 */
public interface DayFormatter {
    /**
     * @param day the day
     * @return a label for the day
     */
    String format(CalendarDay day);

    public static final DayFormatter DEFAULT = new DateFormatDayFormatter();
}
