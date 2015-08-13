package com.prolificinteractive.materialcalendarview;

import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.prolificinteractive.materialcalendarview.format.DayFormatter;
import com.prolificinteractive.materialcalendarview.format.WeekDayFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * Pager adapter backing the calendar view
 */
class MonthPagerAdapter extends PagerAdapter {

    private final LinkedList<MonthView> currentViews;

    private MonthView.Callbacks callbacks = null;
    private Integer color = null;
    private Integer dateTextAppearance = null;
    private Integer weekDayTextAppearance = null;
    private Boolean showOtherDates = null;
    private CalendarDay minDate = null;
    private CalendarDay maxDate = null;
    private DateRangeIndex rangeIndex;
    private CalendarDay selectedDate = null;
    private WeekDayFormatter weekDayFormatter = WeekDayFormatter.DEFAULT;
    private DayFormatter dayFormatter = DayFormatter.DEFAULT;
    private List<DayViewDecorator> decorators = new ArrayList<>();
    private List<DecoratorResult> decoratorResults = null;
    private int firstDayOfTheWeek = Calendar.SUNDAY;


    MonthPagerAdapter() {
        currentViews = new LinkedList<>();
        setRangeDates(null, null);
    }


    public void setDecorators(List<DayViewDecorator> decorators) {
        this.decorators = decorators;
        invalidateDecorators();
    }

    public void invalidateDecorators() {
        decoratorResults = new ArrayList<>();
        for (DayViewDecorator decorator : decorators) {
            DayViewFacade facade = new DayViewFacade();
            decorator.decorate(facade);
            if (facade.isDecorated()) {
                decoratorResults.add(new DecoratorResult(decorator, facade));
            }
        }
        for (MonthView monthView : currentViews) {
            monthView.setDayViewDecorators(decoratorResults);
        }
    }

    @Override
    public int getCount() {
        return rangeIndex.getCount();
    }

    public int getIndexForDay(CalendarDay day) {
        if (day == null) {
            return getCount() / 2;
        }
        if (minDate != null && day.isBefore(minDate)) {
            return 0;
        }
        if (maxDate != null && day.isAfter(maxDate)) {
            return getCount() - 1;
        }
        return rangeIndex.indexOf(day);
    }

    @Override
    public int getItemPosition(Object object) {
        if (!(object instanceof MonthView)) {
            return POSITION_NONE;
        }
        MonthView monthView = (MonthView) object;
        CalendarDay month = monthView.getMonth();
        if (month == null) {
            return POSITION_NONE;
        }
        int index = rangeIndex.indexOf(month);
        if (index < 0) {
            return POSITION_NONE;
        }
        return index;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        CalendarDay month = getItem(position);
        MonthView monthView = new MonthView(container.getContext(), month, firstDayOfTheWeek);
        monthView.setAlpha(0);

        monthView.setWeekDayFormatter(weekDayFormatter);
        monthView.setDayFormatter(dayFormatter);
        monthView.setCallbacks(callbacks);
        if (color != null) {
            monthView.setSelectionColor(color);
        }
        if (dateTextAppearance != null) {
            monthView.setDateTextAppearance(dateTextAppearance);
        }
        if (weekDayTextAppearance != null) {
            monthView.setWeekDayTextAppearance(weekDayTextAppearance);
        }
        if (showOtherDates != null) {
            monthView.setShowOtherDates(showOtherDates);
        }
        monthView.setMinimumDate(minDate);
        monthView.setMaximumDate(maxDate);
        monthView.setSelectedDate(selectedDate);

        container.addView(monthView);
        currentViews.add(monthView);

        monthView.setDayViewDecorators(decoratorResults);

        return monthView;
    }

    public void setFirstDayOfWeek(int day) {
        firstDayOfTheWeek = day;
        for (MonthView monthView : currentViews) {
            monthView.setFirstDayOfWeek(firstDayOfTheWeek);
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        MonthView monthView = (MonthView) object;
        currentViews.remove(monthView);
        container.removeView(monthView);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void setCallbacks(MonthView.Callbacks callbacks) {
        this.callbacks = callbacks;
        for (MonthView monthView : currentViews) {
            monthView.setCallbacks(callbacks);
        }
    }

    public void setSelectionColor(int color) {
        this.color = color;
        for (MonthView monthView : currentViews) {
            monthView.setSelectionColor(color);
        }
    }

    public void setDateTextAppearance(int taId) {
        if (taId == 0) {
            return;
        }
        this.dateTextAppearance = taId;
        for (MonthView monthView : currentViews) {
            monthView.setDateTextAppearance(taId);
        }
    }

    public void setShowOtherDates(boolean show) {
        this.showOtherDates = show;
        for (MonthView monthView : currentViews) {
            monthView.setShowOtherDates(show);
        }
    }

    public void setWeekDayFormatter(WeekDayFormatter formatter) {
        this.weekDayFormatter = formatter;
        for (MonthView monthView : currentViews) {
            monthView.setWeekDayFormatter(formatter);
        }
    }

    public void setDayFormatter(DayFormatter formatter) {
        this.dayFormatter = formatter;
        for (MonthView monthView : currentViews) {
            monthView.setDayFormatter(formatter);
        }
    }

    public boolean getShowOtherDates() {
        return showOtherDates;
    }

    public void setWeekDayTextAppearance(int taId) {
        if (taId == 0) {
            return;
        }
        this.weekDayTextAppearance = taId;
        for (MonthView monthView : currentViews) {
            monthView.setWeekDayTextAppearance(taId);
        }
    }

    public void setRangeDates(CalendarDay min, CalendarDay max) {
        this.minDate = min;
        this.maxDate = max;
        for (MonthView monthView : currentViews) {
            monthView.setMinimumDate(min);
            monthView.setMaximumDate(max);
        }

        if (min == null) {
            Calendar worker = CalendarUtils.getInstance();
            worker.add(Calendar.YEAR, -200);
            min = CalendarDay.from(worker);
        }

        if (max == null) {
            Calendar worker = CalendarUtils.getInstance();
            worker.add(Calendar.YEAR, 200);
            max = CalendarDay.from(worker);
        }

        rangeIndex = new DateRangeIndex(min, max);

        CalendarDay prevDate = selectedDate;
        notifyDataSetChanged();
        setSelectedDate(prevDate);
        if (prevDate != null) {
            if (!prevDate.equals(selectedDate)) {
                callbacks.onDateChanged(selectedDate);
            }
        }
    }

    public void setSelectedDate(@Nullable CalendarDay date) {
        CalendarDay prevDate = selectedDate;
        this.selectedDate = getValidSelectedDate(date);
        for (MonthView monthView : currentViews) {
            monthView.setSelectedDate(selectedDate);
        }

        if (date == null && prevDate != null) {
            callbacks.onDateChanged(null);
        }
    }

    private CalendarDay getValidSelectedDate(CalendarDay date) {
        if (date == null) {
            return null;
        }
        if (minDate != null && minDate.isAfter(date)) {
            return minDate;
        }
        if (maxDate != null && maxDate.isBefore(date)) {
            return maxDate;
        }
        return date;
    }

    public CalendarDay getItem(int position) {
        return rangeIndex.getItem(position);
    }

    public CalendarDay getSelectedDate() {
        return selectedDate;
    }

    protected int getDateTextAppearance() {
        return dateTextAppearance == null ? 0 : dateTextAppearance;
    }

    protected int getWeekDayTextAppearance() {
        return weekDayTextAppearance == null ? 0 : weekDayTextAppearance;
    }

    public int getFirstDayOfWeek() {
        return firstDayOfTheWeek;
    }

}
