package com.prolificinteractive.materialcalendarview;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;

import java.util.Collection;

/**
 * Display a two week of {@linkplain DayView}s and
 * fourteen {@linkplain WeekDayView}s.
 */
@SuppressLint("ViewConstructor")
public class TwoWeekView extends CalendarPagerView {

    public TwoWeekView(@NonNull MaterialCalendarView view, CalendarDay firstViewDay,
                       final DayOfWeek firstDayOfWeek, boolean showWeekDays) {
        super(view, firstViewDay, firstDayOfWeek, showWeekDays);
    }

    @Override
    protected void buildDayViews(Collection<DayView> dayViews, final LocalDate calendar) {
        LocalDate temp = calendar;
        for (int i = 0; i < (DEFAULT_DAYS_IN_WEEK * 2); i++) {
            addDayView(dayViews, temp);
            temp = temp.plusDays(1);
        }
    }

    @Override
    protected boolean isDayEnabled(CalendarDay day) {
        return true;
    }

    @Override
    protected int getRows() {
        return showWeekDays ? DAY_NAMES_ROW + 2 : 2;
    }
}
