package com.prolificinteractive.materialcalendarview;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Collection;

/**
 * Display a week of {@linkplain DayView}s and
 * seven {@linkplain WeekDayView}s.
 */
@Experimental
@SuppressLint("ViewConstructor")
public class WeekView extends CalendarPagerView {

    public WeekView(@NonNull MaterialCalendarView view, CalendarDay firstViewDay,
                    int firstDayOfWeek, boolean showWeekDays) {
        super(view, firstViewDay, firstDayOfWeek, showWeekDays);
    }

    @Override
    protected void buildDayViews(Collection<DayView> dayViews, Calendar calendar) {
        if (showWeekNumbers()) {
            addWeekNumberView(calendar);
        }
        for (int i = 0; i < DEFAULT_DAYS_IN_WEEK; i++) {
            addDayView(dayViews, calendar);
        }
    }

    @Override
    protected boolean isDayEnabled(CalendarDay day) {
        return true;
    }

    @Override
    protected int getRows() {
        return showWeekDays ? DAY_NAMES_ROW + 1 : 1;
    }

    @Override
    protected int getCols () {
        return DEFAULT_DAYS_IN_WEEK + (showWeekNumbers() ? 1 : 0);
    }
}
