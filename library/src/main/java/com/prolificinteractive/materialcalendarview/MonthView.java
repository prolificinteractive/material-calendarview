package com.prolificinteractive.materialcalendarview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CheckedTextView;

import com.prolificinteractive.materialcalendarview.format.DayFormatter;
import com.prolificinteractive.materialcalendarview.format.WeekDayFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_WEEK;

/**
 * Display a month of {@linkplain DayView}s and
 * seven {@linkplain WeekDayView}s.
 */
@SuppressLint("ViewConstructor")
class MonthView extends ViewGroup implements View.OnClickListener {

    protected static final int DEFAULT_DAYS_IN_WEEK = 7;
    protected static final int DEFAULT_MAX_WEEKS = 6;
    protected static final int DEFAULT_MONTH_TILE_HEIGHT = DEFAULT_MAX_WEEKS + 1;

    public interface Callbacks {

        void onDateChanged(CalendarDay date);
    }

    private Callbacks callbacks;

    private final ArrayList<WeekDayView> weekDayViews = new ArrayList<>();
    private final ArrayList<CheckableCalendarDayView> monthDayViews = new ArrayList<>();

    private final CalendarDay month;
    private int firstDayOfWeek;

    private final Calendar tempWorkingCalendar = CalendarUtils.getInstance();

    private CalendarDay selection = null;
    private List<CalendarDay> selectionList = null;
    private CalendarDay minDate = null;
    private CalendarDay maxDate = null;

    private boolean showOtherDates = false;

    private final ArrayList<DecoratorResult> decoratorResults = new ArrayList<>();


    public MonthView(Context context, CalendarDay month, int firstDayOfWeek, OnLongClickListener olcl, OnTouchListener otl, DayViewProvider dayViewProvider) {
        super(context);
        this.month = month;
        this.firstDayOfWeek = firstDayOfWeek;

        setClipChildren(false);
        setClipToPadding(false);

        Calendar calendar = resetAndGetWorkingCalendar();

        for (int i = 0; i < DEFAULT_DAYS_IN_WEEK; i++) {
            WeekDayView weekDayView = new WeekDayView(context, CalendarUtils.getDayOfWeek(calendar));
            weekDayViews.add(weekDayView);
            addView(weekDayView);
            calendar.add(DATE, 1);
        }

        calendar = resetAndGetWorkingCalendar();

        for (int r = 0; r < DEFAULT_MAX_WEEKS; r++) {
            for (int i = 0; i < DEFAULT_DAYS_IN_WEEK; i++) {
                // TODO something...
                CalendarDay day = CalendarDay.from(calendar);
                View dayView = dayViewProvider != null ? dayViewProvider.getDayView() : new DayView(context);
                ((CheckableCalendarDayView) dayView).setDay(day);
                dayView.setOnClickListener(this);
                dayView.setOnLongClickListener(olcl);
                dayView.setOnTouchListener(otl);
                monthDayViews.add((CheckableCalendarDayView) dayView);
                addView(dayView, new LayoutParams());

                calendar.add(DATE, 1);
            }
        }
        setSelectedDate(CalendarDay.today());
    }

    public Calendar getDateByCoordinate(int x, int y) {
        int test2[];
        int dayViewSize = getMeasuredWidth() / DEFAULT_DAYS_IN_WEEK;
        for (int i = 0; i < getChildCount(); i++) {
            test2 = new int[2];
            getChildAt(i).getLocationInWindow(test2);
            if (getChildAt(i) instanceof CheckableCalendarDayView && x < test2[0] + dayViewSize && y < test2[1] + dayViewSize) {
                return ((CheckableCalendarDayView) getChildAt(i)).getDate().getCalendar();
            }
        }
        return null;
    }

    void setDayViewDecorators(List<DecoratorResult> results) {
        this.decoratorResults.clear();
        if (results != null) {
            this.decoratorResults.addAll(results);
        }
        invalidateDecorators();
    }

    public void setWeekDayTextAppearance(int taId) {
        for (WeekDayView weekDayView : weekDayViews) {
            weekDayView.setTextAppearance(getContext(), taId);
        }
    }

    public void setDateTextAppearance(int taId) {
        for (CheckableCalendarDayView dayView : monthDayViews) {
            if (dayView instanceof CheckedTextView) {
                ((CheckedTextView) dayView).setTextAppearance(getContext(), taId);
            }
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
        return month;
    }

    public void setSelectionColor(int color) {
        for (CheckableCalendarDayView dayView : monthDayViews) {
            dayView.setSelectionColor(color);
        }
    }

    private Calendar resetAndGetWorkingCalendar() {
        month.copyTo(tempWorkingCalendar);
        tempWorkingCalendar.setFirstDayOfWeek(firstDayOfWeek);
        int dow = CalendarUtils.getDayOfWeek(tempWorkingCalendar);
        int delta = firstDayOfWeek - dow;
        //If the delta is positive, we want to remove a week
        boolean removeRow = showOtherDates ? delta >= 0 : delta > 0;
        if (removeRow) {
            delta -= DEFAULT_DAYS_IN_WEEK;
        }
        tempWorkingCalendar.add(DATE, delta);
        return tempWorkingCalendar;
    }

    public void setFirstDayOfWeek(int dayOfWeek) {
        this.firstDayOfWeek = dayOfWeek;

        Calendar calendar = resetAndGetWorkingCalendar();
        calendar.set(DAY_OF_WEEK, dayOfWeek);
        for (WeekDayView dayView : weekDayViews) {
            dayView.setDayOfWeek(calendar);
            calendar.add(DATE, 1);
        }

        calendar = resetAndGetWorkingCalendar();
        for (CheckableCalendarDayView dayView : monthDayViews) {
            CalendarDay day = CalendarDay.from(calendar);
            dayView.setDay(day);
            calendar.add(DATE, 1);
        }

        updateUi();
    }

    public void setWeekDayFormatter(WeekDayFormatter formatter) {
        for (WeekDayView dayView : weekDayViews) {
            dayView.setWeekDayFormatter(formatter);
        }
    }

    public void setDayFormatter(DayFormatter formatter) {
        for (CheckableCalendarDayView dayView : monthDayViews) {
            dayView.setDayFormatter(formatter);
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
        selectionList = null;
        selection = cal;
        updateUi();
    }

    public void setSelectedDateRange(List<CalendarDay> dateRange) {
        selection = null;
        selectionList = new ArrayList<>();
        selectionList.addAll(dateRange);
        updateUi();
    }

    private void updateUi() {
        int ourMonth = month.getMonth();
        for (CheckableCalendarDayView dayView : monthDayViews) {
            CalendarDay day = dayView.getDate();
            dayView.setupSelection(showOtherDates, day.isInRange(minDate, maxDate), day.getMonth() == ourMonth);
            if (selectionList != null) {
                dayView.setChecked(selectionList.contains(day));
            } else if (selection != null) {
                dayView.setChecked(day.equals(selection));
            }
        }
        postInvalidate();
    }

    private void invalidateDecorators() {
        final DayViewFacade facadeAccumulator = new DayViewFacade();
        for (CheckableCalendarDayView dayView : monthDayViews) {
            facadeAccumulator.reset();
            for (DecoratorResult result : decoratorResults) {
                if (result.decorator.shouldDecorate(dayView.getDate())) {
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
        if (v instanceof CheckableCalendarDayView) {
            for (CheckableCalendarDayView other : monthDayViews) {
                other.setChecked(false);
            }
            CheckableCalendarDayView dayView = (CheckableCalendarDayView) v;
            dayView.setChecked(true);

            CalendarDay date = dayView.getDate();
            if (date.equals(selection)) {
                return;
            } else if (selectionList != null && selectionList.contains(date)) {
                return;
            }
            selection = date;

            if (callbacks != null) {
                callbacks.onDateChanged(dayView.getDate());
            }
        }
    }

    /*
     * Custom ViewGroup Code
     */

    /**
     * {@inheritDoc}
     */
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        final int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int specHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        final int specHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        //We expect to be somewhere inside a MaterialCalendarView, which should measure EXACTLY
        if (specHeightMode == MeasureSpec.UNSPECIFIED || specWidthMode == MeasureSpec.UNSPECIFIED) {
            throw new IllegalStateException("MonthView should never be left to decide it's size");
        }

        //The spec width should be a correct multiple
        final int measureTileSize = specWidthSize / DEFAULT_DAYS_IN_WEEK;

        //Just use the spec sizes
        setMeasuredDimension(specWidthSize, specHeightSize);

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);

            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                    measureTileSize,
                    MeasureSpec.EXACTLY
            );

            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                    measureTileSize,
                    MeasureSpec.EXACTLY
            );

            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int count = getChildCount();

        final int parentLeft = 0;
        final int parentTop = 0;

        int childTop = parentTop;
        int childLeft = parentLeft;

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);

            final int width = child.getMeasuredWidth();
            final int height = child.getMeasuredHeight();

            child.layout(childLeft, childTop, childLeft + width, childTop + height);

            childLeft += width;

            //We should warp every so many children
            if (i % DEFAULT_DAYS_IN_WEEK == (DEFAULT_DAYS_IN_WEEK - 1)) {
                childLeft = parentLeft;
                childTop += height;
            }

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams();
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams();
    }


    @Override
    public void onInitializeAccessibilityEvent(@NonNull AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(MonthView.class.getName());
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(@NonNull AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(MonthView.class.getName());
    }

    /**
     * Simple layout params class for MonthView, since every child is the same size
     */
    private static class LayoutParams extends MarginLayoutParams {

        /**
         * {@inheritDoc}
         */
        public LayoutParams() {
            super(WRAP_CONTENT, WRAP_CONTENT);
        }
    }
}
