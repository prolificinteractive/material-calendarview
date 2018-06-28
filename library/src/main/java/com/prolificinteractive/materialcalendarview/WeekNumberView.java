package com.prolificinteractive.materialcalendarview;

import android.content.Context;
import android.support.annotation.NonNull;

import com.prolificinteractive.materialcalendarview.format.DateFormatDayFormatter;
import com.prolificinteractive.materialcalendarview.format.DayFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Display the week number similarly to {@linkplain DayView}
 */
@Experimental
class WeekNumberView extends DayView {

    private int weekNumber;

    /**
     * @param day       first day of the week
     */
    public WeekNumberView ( Context context, CalendarDay day ) {
        super(context);

        setWeekNumber(day);
        setDayFormatter(new DateFormatDayFormatter(new SimpleDateFormat("w", Locale.getDefault())));
        setDay(day);
    }

    private void setWeekNumber(@NonNull CalendarDay day) {
        this.weekNumber = day.getCalendar().get(Calendar.WEEK_OF_YEAR);
    }

    public int getWeekNumber() {
        return weekNumber;
    }
}
