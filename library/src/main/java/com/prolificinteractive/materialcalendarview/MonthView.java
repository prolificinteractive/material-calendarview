package com.prolificinteractive.materialcalendarview;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.Calendar;
import java.util.Collection;

import static com.prolificinteractive.materialcalendarview.MaterialCalendarView.showOtherMonths;
import static java.util.Calendar.DATE;

/**
 * Display a month of {@linkplain DayView}s and
 * seven {@linkplain WeekDayView}s.
 */
@SuppressLint("ViewConstructor")
class MonthView extends CalendarPagerView implements View.OnClickListener {

    protected static final int DEFAULT_DAYS_IN_WEEK = 7;
    protected static final int DEFAULT_MAX_WEEKS = 6;
    protected static final int DEFAULT_MONTH_TILE_HEIGHT = DEFAULT_MAX_WEEKS + 1;
    private static final Calendar tempWorkingCalendar = CalendarUtils.getInstance();

    public MonthView(@NonNull MaterialCalendarView view, CalendarDay month, int firstDayOfWeek) {
        super(view, month, firstDayOfWeek);
    }

    @Override
    protected void buildDayViews(Collection<DayView> dayViews, Calendar calendar) {
        for (int r = 0; r < DEFAULT_MAX_WEEKS; r++) {
            for (int i = 0; i < DEFAULT_DAYS_IN_WEEK; i++) {
                CalendarDay day = CalendarDay.from(calendar);
                DayView dayView = new DayView(getContext(), day);
                dayView.setOnClickListener(this);
                dayViews.add(dayView);
                addView(dayView, new LayoutParams());

                calendar.add(DATE, 1);
            }
        }
    }

    public CalendarDay getMonth() {
        return getFirstViewDay();
    }

    @Override
    protected Calendar resetAndGetWorkingCalendar() {
        getFirstViewDay().copyTo(tempWorkingCalendar);
        //noinspection ResourceType
        tempWorkingCalendar.setFirstDayOfWeek(getFirstDayOfWeek());
        int dow = CalendarUtils.getDayOfWeek(tempWorkingCalendar);
        int delta = getFirstDayOfWeek() - dow;
        //If the delta is positive, we want to remove a week
        boolean removeRow = showOtherMonths(showOtherDates) ? delta >= 0 : delta > 0;
        if (removeRow) {
            delta -= DEFAULT_DAYS_IN_WEEK;
        }
        tempWorkingCalendar.add(DATE, delta);
        return tempWorkingCalendar;
    }

    @Override
    protected boolean isDayEnabled(CalendarDay day) {
        return day.getMonth() == getFirstViewDay().getMonth();
    }
}
