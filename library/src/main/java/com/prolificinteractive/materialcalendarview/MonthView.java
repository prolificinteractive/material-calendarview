package com.prolificinteractive.materialcalendarview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.Calendar;

import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.SUNDAY;

/**
 * Display a month of {@linkplain DayView}s and
 * seven {@linkplain WeekDayView}s.
 */
class MonthView extends GridLayout implements View.OnClickListener {

    public static interface Callbacks {

        public void onDateChanged(CalendarDay date);
    }

    private Callbacks callbacks;

    private final ArrayList<WeekDayView> weekDayViews = new ArrayList<>();
    private final ArrayList<DayView> monthDayViews = new ArrayList<>();

    private final Calendar calendarOfRecord = CalendarUtils.getInstance();
    private final Calendar tempWorkingCalendar = CalendarUtils.getInstance();
    private int firstDayOfWeek = SUNDAY;

    private CalendarDay selection = null;
    private CalendarDay minDate = null;
    private CalendarDay maxDate = null;

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
        setSelectedDate(new CalendarDay());
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

    private Calendar resetAndGetWorkingCalendar() {
        CalendarUtils.copyDateTo(calendarOfRecord, tempWorkingCalendar);
        int dow = CalendarUtils.getDayOfWeek(tempWorkingCalendar);
        int delta = firstDayOfWeek - dow;
        //If the delta is positive, we want to remove a week
        boolean removeRow = showOtherDates ? delta >= 0 : delta > 0;
        if(removeRow) {
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
            dayView.setDayOfWeek(calendar);
            calendar.add(DATE, 1);
        }
    }

    public void setMinimumDate(CalendarDay minDate) {
        this.minDate = minDate;
        updateUi();
    }

    public void setMaximumDate(CalendarDay maxDate) {
        this.maxDate = maxDate;
        updateUi();
    }

    public void setDate(CalendarDay month) {
        month.copyTo(calendarOfRecord);
        CalendarUtils.setToFirstDay(calendarOfRecord);
        updateUi();
    }

    public void setSelectedDate(CalendarDay cal) {
        selection = cal;
        updateUi();
    }

    private void updateUi() {
        int ourMonth = CalendarUtils.getMonth(calendarOfRecord);
        Calendar calendar = resetAndGetWorkingCalendar();
        for(DayView dayView : monthDayViews) {
            CalendarDay day = new CalendarDay(calendar);
            dayView.setDay(day);
            dayView.setupSelection(showOtherDates, day.isInRange(minDate, maxDate), day.getMonth() == ourMonth);
            dayView.setChecked(day.equals(selection));
            calendar.add(DATE, 1);
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
