package com.prolificinteractive.materialcalendarview.format;

import android.support.annotation.NonNull;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Format using a {@linkplain DateFormat} instance.
 */
public class DateFormatDayFormatter implements DayFormatter {

    private final DateFormat dateFormat;

    /**
     * Uses "MMMM yyyy" for formatting
     */
    public DateFormatDayFormatter() {
        this.dateFormat = new SimpleDateFormat("d", Locale.getDefault());
    }

    /**
     * @param format the format to use
     */
    public DateFormatDayFormatter(@NonNull DateFormat format) {
        this.dateFormat = format;
    }

    @Override
    public @NonNull String format(@NonNull CalendarDay day) {
        return dateFormat.format(day.getDate());
    }
}
