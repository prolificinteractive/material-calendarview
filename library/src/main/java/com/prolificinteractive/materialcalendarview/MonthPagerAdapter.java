package com.prolificinteractive.materialcalendarview;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView.ShowOtherDates;
import com.prolificinteractive.materialcalendarview.format.DayFormatter;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;
import com.prolificinteractive.materialcalendarview.format.WeekDayFormatter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Pager adapter backing the calendar view
 */
class MonthPagerAdapter extends PagerAdapter {

    private final ArrayDeque<MonthView> currentViews;

    private final MaterialCalendarView mcv;
    private final CalendarDay today;

    private TitleFormatter titleFormatter = null;
    private Integer color = null;
    private Integer dateTextAppearance = null;
    private Integer weekDayTextAppearance = null;
    @ShowOtherDates
    private int showOtherDates = MaterialCalendarView.SHOW_DEFAULTS;
    private CalendarDay minDate = null;
    private CalendarDay maxDate = null;
    private DateRangeIndex rangeIndex;
    private List<CalendarDay> selectedDates = new ArrayList<>();
    private WeekDayFormatter weekDayFormatter = WeekDayFormatter.DEFAULT;
    private DayFormatter dayFormatter = DayFormatter.DEFAULT;
    private List<DayViewDecorator> decorators = new ArrayList<>();
    private List<DecoratorResult> decoratorResults = null;
    private int firstDayOfTheWeek = Calendar.SUNDAY;
    private boolean selectionEnabled = true;

    MonthPagerAdapter(MaterialCalendarView mcv) {
        this.mcv = mcv;
        this.today = CalendarDay.today();
        currentViews = new ArrayDeque<>();
        currentViews.iterator();
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

    @Override
    public CharSequence getPageTitle(int position) {
        return titleFormatter == null ? "" : titleFormatter.format(getItem(position));
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
        MonthView monthView = new MonthView(mcv, month, firstDayOfTheWeek);
        monthView.setAlpha(0);
        monthView.setSelectionEnabled(selectionEnabled);

        monthView.setWeekDayFormatter(weekDayFormatter);
        monthView.setDayFormatter(dayFormatter);
        if (color != null) {
            monthView.setSelectionColor(color);
        }
        if (dateTextAppearance != null) {
            monthView.setDateTextAppearance(dateTextAppearance);
        }
        if (weekDayTextAppearance != null) {
            monthView.setWeekDayTextAppearance(weekDayTextAppearance);
        }
        monthView.setShowOtherDates(showOtherDates);
        monthView.setMinimumDate(minDate);
        monthView.setMaximumDate(maxDate);
        monthView.setSelectedDates(selectedDates);

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

    public void setSelectionEnabled(boolean enabled) {
        selectionEnabled = enabled;
        for (MonthView monthView : currentViews) {
            monthView.setSelectionEnabled(selectionEnabled);
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

    public void setTitleFormatter(@NonNull TitleFormatter titleFormatter) {
        this.titleFormatter = titleFormatter;
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

    public void setShowOtherDates(@ShowOtherDates int showFlags) {
        this.showOtherDates = showFlags;
        for (MonthView monthView : currentViews) {
            monthView.setShowOtherDates(showFlags);
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

    @ShowOtherDates
    public int getShowOtherDates() {
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
            min = CalendarDay.from(today.getYear() - 200, today.getMonth(), today.getDay());
        }

        if (max == null) {
            max = CalendarDay.from(today.getYear() + 200, today.getMonth(), today.getDay());
        }

        rangeIndex = new DateRangeIndex(min, max);

        notifyDataSetChanged();
        invalidateSelectedDates();
    }

    public void clearSelections() {
        selectedDates.clear();
        invalidateSelectedDates();
    }

    public void setDateSelected(CalendarDay day, boolean selected) {
        if (selected) {
            if (!selectedDates.contains(day)) {
                selectedDates.add(day);
                invalidateSelectedDates();
            }
        } else {
            if (selectedDates.contains(day)) {
                selectedDates.remove(day);
                invalidateSelectedDates();
            }
        }
    }

    private void invalidateSelectedDates() {
        validateSelectedDates();
        for (MonthView monthView : currentViews) {
            monthView.setSelectedDates(selectedDates);
        }
    }

    private void validateSelectedDates() {
        for (int i = 0; i < selectedDates.size(); i++) {
            CalendarDay date = selectedDates.get(i);

            if ((minDate != null && minDate.isAfter(date)) || (maxDate != null && maxDate.isBefore(date))) {
                selectedDates.remove(i);
                mcv.onDateUnselected(date);
                i -= 1;
            }
        }
    }

    public CalendarDay getItem(int position) {
        return rangeIndex.getItem(position);
    }

    @NonNull
    public List<CalendarDay> getSelectedDates() {
        return Collections.unmodifiableList(selectedDates);
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
