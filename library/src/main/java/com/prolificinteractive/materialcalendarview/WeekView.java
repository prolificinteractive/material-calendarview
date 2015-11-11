package com.prolificinteractive.materialcalendarview;

import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Collection;

public class WeekView extends CalendarPagerView {

    protected static final int DEFAULT_DAYS_IN_WEEK = 7;

    public WeekView(@NonNull MaterialCalendarView view,
                    CalendarDay firstViewDay,
                    int firstDayOfWeek) {
        super(view, firstViewDay, firstDayOfWeek);
    }

    @Override
    protected void buildDayViews(Collection<DayView> dayViews, Calendar calendar) {
        for (int i = 0; i < DEFAULT_DAYS_IN_WEEK; i++) {
            addDayView(dayViews, calendar);
        }
    }

    @Override
    protected boolean isDayEnabled(CalendarDay day) {
        return true;
    }
}
