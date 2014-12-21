package com.adamkoski.calendarwidget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.Calendar;

import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.SUNDAY;

/**
 * Display a month of days
 */
class MonthView extends GridLayout implements View.OnClickListener {

    public static interface Callbacks {

        public void onDateChanged(CalendarDay date);
    }

    private Callbacks callbacks;

    private ArrayList<WeekDayView> weekDayViews = new ArrayList<>();
    private ArrayList<DayView> monthDayViews = new ArrayList<>();

    private Calendar calendarOfRecord = CalendarUtils.copy(Calendar.getInstance());
    private Calendar tempWorkingCalendar = CalendarUtils.copy(Calendar.getInstance());
    private int firstDayOfWeek = SUNDAY;
    private CalendarDay selection = new CalendarDay(calendarOfRecord);

    public MonthView(Context context) {
        super(context);
        init();
    }

    public MonthView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MonthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MonthView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {

        setColumnCount(7);
        setRowCount(7);
        setClipChildren(false);
        setPadding(8, 8, 8, 8);
        setClipToPadding(false);

        for(int i = 0; i < 7; i++) {
            WeekDayView dayView = new WeekDayView(getContext());
            addView(dayView);
            weekDayViews.add(dayView);
        }
        setFirstDayOfWeek(firstDayOfWeek);

        for(int i = 0; i < 42; i++) {
            DayView dayView = new DayView(getContext());
            addView(dayView);
            monthDayViews.add(dayView);
            dayView.setOnClickListener(this);
        }
        setTime(Calendar.getInstance());
    }

    private Calendar resetAndGetWorkingCalendar() {
        CalendarUtils.copyDateTo(calendarOfRecord, tempWorkingCalendar);
        int dow = tempWorkingCalendar.get(DAY_OF_WEEK);
        int delta = firstDayOfWeek - dow;
        if(delta >= 0) { //TODO add logic for '>=' week above VS '>' week below
            delta -= 7;
        }
        tempWorkingCalendar.add(DATE, delta);
        return tempWorkingCalendar;
    }

    public void setFirstDayOfWeek(int dayOfWeek) {
        this.firstDayOfWeek = dayOfWeek;

        Calendar calendar = resetAndGetWorkingCalendar();
        calendar.set(DAY_OF_WEEK, dayOfWeek);
        for(WeekDayView dayView : weekDayViews) {
            dayView.setDayOfWeek(calendar.get(DAY_OF_WEEK));
            calendar.add(DATE, 1);
        }
    }

    public void setTime(Calendar cal) {
        CalendarUtils.copyDateTo(cal, calendarOfRecord);
        CalendarUtils.setToFirstDay(calendarOfRecord);

        Calendar calendar = resetAndGetWorkingCalendar();
        for(DayView dayView : monthDayViews) {
            CalendarDay day = new CalendarDay(calendar);
            dayView.setDay(day);
            dayView.setChecked(day.equals(selection));
            calendar.add(DATE, 1);
        }
    }

    public void setCallbacks(Callbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public void onClick(View v) {
        if(v instanceof DayView) {
            for(DayView other : monthDayViews) {
                other.setChecked(false);
            }
            DayView dayView = (DayView) v;
            dayView.setChecked(true);

            CalendarDay date = dayView.getDate();
            if(date.equals(selection)) {
                return;
            }
            selection = date;

            if(callbacks != null) {
                callbacks.onDateChanged(dayView.getDate());
            }
        }
    }
}
