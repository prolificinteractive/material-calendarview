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
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SUNDAY;

/**
 * Display a month of days
 */
class MonthView extends GridLayout implements View.OnClickListener {

    public static interface Callbacks {

        public void onDateChanged(Calendar date);
    }

    private Callbacks callbacks;

    private ArrayList<WeekDayView> weekDayViews = new ArrayList<>();
    private ArrayList<DayView> monthDayViews = new ArrayList<>();

    private Calendar calendar = CalendarUtils.copy(Calendar.getInstance());
    private Calendar tempCal = CalendarUtils.copy(Calendar.getInstance());
    private int dayOfWeek = SUNDAY;
    private int month = calendar.get(MONTH);
    private Calendar selection = CalendarUtils.copy(calendar);

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
        setFirstDayOfWeek(dayOfWeek);

        for(int i = 0; i < 42; i++) {
            DayView dayView = new DayView(getContext());
            addView(dayView);
            monthDayViews.add(dayView);
            dayView.setOnClickListener(this);
        }
        setTime(Calendar.getInstance());
    }

    private Calendar getResetTempCal() {
        CalendarUtils.copyDateTo(calendar, tempCal);
        int dow = tempCal.get(DAY_OF_WEEK);
        int delta = dayOfWeek - dow;
        if(delta >= 0) { //TODO add logic for '>=' week above VS '>' week below
            delta -= 7;
        }
        tempCal.add(DATE, delta);
        return tempCal;
    }

    public void setFirstDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;

        Calendar calendar = getResetTempCal();
        calendar.set(DAY_OF_WEEK, dayOfWeek);
        for(WeekDayView dayView : weekDayViews) {
            dayView.setDayOfWeek(calendar.get(DAY_OF_WEEK));
            calendar.add(DATE, 1);
        }
    }

    public void setTime(Calendar cal) {
        CalendarUtils.copyDateTo(cal, this.calendar);
        this.month = cal.get(MONTH);
        CalendarUtils.setToFirstDay(calendar);

        Calendar calendar = getResetTempCal();
        for(DayView dayView : monthDayViews) {
            dayView.setDay(calendar);
            dayView.setChecked(CalendarUtils.equals(selection, calendar));
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

            Calendar date = dayView.getDate();
            if(CalendarUtils.equals(date, selection)) {
                return;
            }
            CalendarUtils.copyDateTo(date, selection);

            if(callbacks != null) {
                callbacks.onDateChanged(dayView.getDate());
            }
        }
    }
}
