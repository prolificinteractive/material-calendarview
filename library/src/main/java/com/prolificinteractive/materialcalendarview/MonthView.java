package com.prolificinteractive.materialcalendarview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.Date;

import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.SUNDAY;

/**
 * Display a month of {@linkplain com.prolificinteractive.materialcalendarview.DayView}s and
 * seven {@linkplain com.prolificinteractive.materialcalendarview.WeekDayView}s.
 */
class MonthView extends GridLayout implements View.OnClickListener {

    public static interface Callbacks {

        public void onDateChanged(long date);
    }

    private Callbacks callbacks;

    private final ArrayList<WeekDayView> weekDayViews = new ArrayList<>();
    private final ArrayList<DayView> monthDayViews = new ArrayList<>();

    private long calendarOfRecord;
    private int firstDayOfWeek = SUNDAY;

    private long selection = 0;
    private long minDate = 0;
    private long maxDate = 0;

    private boolean showOtherDates = false;

    public MonthView(Context context) {
        this(context, null);
    }

    public MonthView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setColumnCount(7);
        setRowCount(7);

        setClipChildren(false);
        setClipToPadding(false);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int children = getChildCount();
        for(int i = 0; i < children; i++) {
            View child = getChildAt(i);
            if(child instanceof WeekDayView) {
                weekDayViews.add((WeekDayView) child);
            } else if(child instanceof DayView) {
                monthDayViews.add((DayView) child);
                child.setOnClickListener(this);
            }
        }
        setFirstDayOfWeek(firstDayOfWeek);
        setSelectedDate(new Date().getTime());
    }

    public void setWeekDayTextAppearance(int taId) {
        for(WeekDayView weekDayView : weekDayViews) {
            weekDayView.setTextAppearance(getContext(), taId);
        }
    }

    public void setDateTextAppearance(int taId) {
        for(DayView dayView : monthDayViews) {
            dayView.setTextAppearance(getContext(), taId);
        }
    }

    public void setShowOtherDates(boolean show) {
        this.showOtherDates = show;
        updateUi();
    }

    public boolean getShowOtherDates() {
        return showOtherDates;
    }

    public void setSelectionColor(int color) {
        for(DayView dayView : monthDayViews) {
            dayView.setSelectionColor(color);
        }
    }

    private long resetAndGetWorkingCalendar() {
        long tempWorkingCalendar = calendarOfRecord;
        int dow = CalendarHelper.getDayOfWeek(tempWorkingCalendar);
        int delta = firstDayOfWeek - dow;
        //If the delta is positive, we want to remove a week
        boolean removeRow = showOtherDates ? delta >= 0 : delta > 0;
        if(removeRow) {
            delta -= 7;
        }
        tempWorkingCalendar = CalendarHelper.add(tempWorkingCalendar, DATE, delta);
        return tempWorkingCalendar;
    }

    public void setFirstDayOfWeek(int dayOfWeek) {
        this.firstDayOfWeek = dayOfWeek;
        long calendar  = calendarOfRecord;
        calendar = CalendarHelper.set(calendar, DAY_OF_WEEK, dayOfWeek);
        for(WeekDayView dayView : weekDayViews) {
            dayView.setDayOfWeek(CalendarHelper.getDayOfWeek(calendar));
            calendar = CalendarHelper.add(calendar, DATE, 1);
        }
    }

    public void set(long minDate, long maxDate, long date, long selected, boolean showOtherDates) {
        this.minDate = minDate;
        this.maxDate = maxDate;
        calendarOfRecord = date;
        selection = selected;
        this.showOtherDates = showOtherDates;
        updateUi();
    }

    public void setRanges(long minDate, long maxDate){
        this.minDate = minDate;
        this.maxDate = maxDate;
        updateUi();
    }

    public void setSelectedDate(long selectedDate){
        this.selection = selectedDate;
        updateUi();
    }

    public void updateUi() {
        int ourMonth = CalendarHelper.getMonth(calendarOfRecord);
        long calendar = resetAndGetWorkingCalendar();
        for(DayView dayView : monthDayViews) {
            dayView.setDay(calendar);
            dayView.setupSelection(showOtherDates, CalendarHelper.isBetween(calendar, minDate, maxDate), CalendarHelper.getMonth(calendar) == ourMonth);
            dayView.setChecked(calendar == selection);
            calendar = CalendarHelper.add(calendar, DATE, 1);
        }
        postInvalidate();
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

            long date = dayView.getDate();
            if(date == selection) {
                return;
            }
            selection = date;

            if(callbacks != null) {
                callbacks.onDateChanged(dayView.getDate());
            }
        }
    }
}
