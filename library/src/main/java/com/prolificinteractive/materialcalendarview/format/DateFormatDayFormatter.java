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

    private DateFormat dateFormat;
    private Locale lastKnownLocal;

    //create singleton instance from DateFormatDayFormatter to avoid creating redundant instance for each DayView.
    private static DateFormatDayFormatter dateFormatDayFormatter;


    public static DateFormatDayFormatter getInstance() {
        if (dateFormatDayFormatter == null)
            dateFormatDayFormatter = new DateFormatDayFormatter();
        else {
            //if user change local and resume the activity the calender now will draw DayView text with the new Local.
            Locale currentLocal = Locale.getDefault();
            if (!dateFormatDayFormatter.getLastKnownLocal().equals(currentLocal)) {
                dateFormatDayFormatter.setLastKnownLocal(currentLocal);
                dateFormatDayFormatter.setDateFormat(new SimpleDateFormat("d", currentLocal));
            }
        }
        return dateFormatDayFormatter;
    }

    /**
     * Format using a default format
     */
    private DateFormatDayFormatter() {
        this.dateFormat = new SimpleDateFormat("d", Locale.getDefault());
        this.lastKnownLocal = Locale.getDefault();
    }

    /**
     * Format using a specific {@linkplain DateFormat}
     *
     * @param format the format to use
     */
    private DateFormatDayFormatter(@NonNull DateFormat format) {
        this.dateFormat = format;
    }

    public Locale getLastKnownLocal() {
        return lastKnownLocal;
    }

    public void setLastKnownLocal(Locale lastKnownLocal) {
        this.lastKnownLocal = lastKnownLocal;
    }

    public DateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NonNull
    public String format(@NonNull CalendarDay day) {
        return dateFormat.format(day.getDate());
    }
}
