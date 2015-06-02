package com.prolificinteractive.materialcalendarview;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarUtils;
import com.prolificinteractive.materialcalendarview.format.WeekDayFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_WEEK;

/**
 * Display a week of {@linkplain DayView}s and
 * seven {@linkplain WeekDayView}s.
 */
public class WeekView extends LinearLayout implements View.OnClickListener{
    protected static final int DEFAULT_DAYS_IN_WEEK = 7;
    protected static final int DEFAULT_MAX_WEEKS = 1;
    protected static final int DEFAULT_MONTH_TILE_HEIGHT = DEFAULT_MAX_WEEKS + 1;

    public interface Callbacks {

        void onDateChanged(CalendarDay date);
    }

    private Callbacks callbacks;

    private final ArrayList<WeekDayView> weekDayViews = new ArrayList<>();
    private final ArrayList<DayView> monthDayViews = new ArrayList<>();

    private final CalendarDay week;
    private int firstDayOfWeek;

    private final Calendar tempWorkingCalendar = CalendarUtils.getInstance();

    private CalendarDay selection = null;
    private CalendarDay minDate = null;
    private CalendarDay maxDate = null;

    private boolean showOtherDates = false;

    private final ArrayList<DecoratorResult> decoratorResults = new ArrayList<>();


    public WeekView(Context context, CalendarDay week, int firstDayOfWeek) {
        super(context);
        this.week = week;
        this.firstDayOfWeek = firstDayOfWeek;

        setOrientation(VERTICAL);

        setClipChildren(false);
        setClipToPadding(false);

        Calendar calendar = resetAndGetWorkingCalendar();

        LinearLayout row = makeRow(this);
        for (int i = 0; i < DEFAULT_DAYS_IN_WEEK; i++) {
            WeekDayView weekDayView = new WeekDayView(context, CalendarUtils.getDayOfWeek(calendar));
            weekDayViews.add(weekDayView);
            row.addView(weekDayView, new LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));
            calendar.add(DATE, 1);
        }

        calendar = resetAndGetWorkingCalendar();

        for(int r = 0; r < DEFAULT_MAX_WEEKS; r++) {
            row = makeRow(this);
            for(int i = 0; i < DEFAULT_DAYS_IN_WEEK; i++) {
                CalendarDay day = new CalendarDay(calendar);
                DayView dayView = new DayView(context, day);
                dayView.setOnClickListener(this);
                monthDayViews.add(dayView);
                row.addView(dayView, new LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));

                calendar.add(DATE, 1);
            }
        }

        setSelectedDate(new CalendarDay());
    }


    void setDayViewDecorators(List<DecoratorResult> results) {
        this.decoratorResults.clear();
        if(results != null) {
            this.decoratorResults.addAll(results);
        }
        invalidateDecorators();
    }

    private static LinearLayout makeRow(LinearLayout parent) {
        LinearLayout row = new LinearLayout(parent.getContext());
        row.setOrientation(HORIZONTAL);
        parent.addView(row, new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f));
        return row;
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

    public CalendarDay getMonth() {
        return week;
    }

    public void setSelectionColor(int color) {
        for(DayView dayView : monthDayViews) {
            dayView.setSelectionColor(color);
        }
    }

    private Calendar resetAndGetWorkingCalendar() {
        week.copyTo(tempWorkingCalendar);
//        tempWorkingCalendar.setFirstDayOfWeek(firstDayOfWeek);
        int dow = CalendarUtils.getDayOfWeek(tempWorkingCalendar);
        int delta = firstDayOfWeek - dow;
        //If the delta is positive, we want to remove a week
        boolean removeRow = showOtherDates ? delta >= 0 : delta > 0;
        if(removeRow) {
            delta -= DEFAULT_DAYS_IN_WEEK;
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

        calendar = resetAndGetWorkingCalendar();
        for(DayView dayView : monthDayViews) {
            CalendarDay day = new CalendarDay(calendar);
            dayView.setDay(day);
            calendar.add(DATE, 1);
        }

        updateUi();
    }

    public void setWeekDayFormatter(WeekDayFormatter formatter) {
        for(WeekDayView dayView : weekDayViews) {
            dayView.setWeekDayFormatter(formatter);
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

    public void setSelectedDate(CalendarDay cal) {
        selection = cal;
        updateUi();
    }

    private void updateUi() {
        int ourWeek = week.getWeek();
        for(DayView dayView : monthDayViews) {
            CalendarDay day = dayView.getDate();
            dayView.setupSelection(showOtherDates, day.isInRange(minDate, maxDate), day.getWeek() == ourWeek);
            dayView.setChecked(day.equals(selection));
        }
        postInvalidate();
    }

    private void invalidateDecorators() {
        final DayViewFacade facadeAccumulator = new DayViewFacade();
        for(DayView dayView : monthDayViews) {
            facadeAccumulator.reset();
            for(DecoratorResult result : decoratorResults) {
                if(result.decorator.shouldDecorate(dayView.getDate())) {
                    result.result.applyTo(facadeAccumulator);
                }
            }
            dayView.applyFacade(facadeAccumulator);
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
