package com.adamkoski.calendarwidget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Display a month of days
 */
class MonthView extends GridLayout {

    private ArrayList<WeekDayView> weekDayViews = new ArrayList<>();
    private ArrayList<DayView> monthDayViews = new ArrayList<>();

    private Calendar calendar = Calendar.getInstance();
    private Calendar tempCal = Calendar.getInstance();
    private int dayOfWeek = Calendar.SUNDAY;

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

        for(int i = 0; i < 7; i++) {
            WeekDayView dayView = new WeekDayView(getContext());
            addView(dayView);
            weekDayViews.add(dayView);
        }
        setFirstDayOfWeek(dayOfWeek);

        for(int i = 0; i < 42; i++) {
            DayView dayView = new DayView(getContext());
            addView(dayView);
            monthDayViews.add(dayView);
        }
        Calendar cal = Calendar.getInstance();
        setTime(cal);
    }

    private Calendar getResetTempCal() {
        tempCal.setTime(calendar.getTime());
        int dow = tempCal.get(Calendar.DAY_OF_WEEK);
        int delta = dayOfWeek - dow;
        if(delta >= 0) { //TODO add logic for '>=' week above VS '>' week below
            delta -= 7;
        }
        tempCal.add(Calendar.DATE, delta);
        return tempCal;
    }

    public void setFirstDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;

        Calendar calendar = getResetTempCal();
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        for(WeekDayView dayView : weekDayViews) {
            dayView.setDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));
            calendar.add(Calendar.DATE, 1);
        }
    }

    public void setTime(Calendar cal) {
        this.calendar.setTime(cal.getTime());
        this.calendar.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 1);

        Calendar calendar = getResetTempCal();
        for(DayView dayView : monthDayViews) {
            dayView.setDay(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
        }
    }
}
