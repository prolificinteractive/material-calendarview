package com.prolificinteractive.materialcalendarview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView.ShowOtherDates;
import com.prolificinteractive.materialcalendarview.format.DayFormatter;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;
import com.prolificinteractive.materialcalendarview.format.WeekDayFormatter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.threeten.bp.LocalDate;

/**
 * Pager adapter backing the calendar view
 */
abstract class CalendarPagerAdapter<V extends CalendarPagerView> extends PagerAdapter {

  private final ArrayDeque<V> currentViews;

  protected final MaterialCalendarView mcv;
  private final CalendarDay today;

  @NonNull private TitleFormatter titleFormatter = TitleFormatter.DEFAULT;
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
  private DayFormatter dayFormatterContentDescription = dayFormatter;
  private List<DayViewDecorator> decorators = new ArrayList<>();
  private List<DecoratorResult> decoratorResults = null;
  private boolean selectionEnabled = true;
  boolean showWeekDays;

  CalendarPagerAdapter(MaterialCalendarView mcv) {
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
    for (V pagerView : currentViews) {
      pagerView.setDayViewDecorators(decoratorResults);
    }
  }

  @Override
  public int getCount() {
    return rangeIndex.getCount();
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return titleFormatter.format(getItem(position));
  }

  public CalendarPagerAdapter<?> migrateStateAndReturn(CalendarPagerAdapter<?> newAdapter) {
    newAdapter.titleFormatter = titleFormatter;
    newAdapter.color = color;
    newAdapter.dateTextAppearance = dateTextAppearance;
    newAdapter.weekDayTextAppearance = weekDayTextAppearance;
    newAdapter.showOtherDates = showOtherDates;
    newAdapter.minDate = minDate;
    newAdapter.maxDate = maxDate;
    newAdapter.selectedDates = selectedDates;
    newAdapter.weekDayFormatter = weekDayFormatter;
    newAdapter.dayFormatter = dayFormatter;
    newAdapter.dayFormatterContentDescription = dayFormatterContentDescription;
    newAdapter.decorators = decorators;
    newAdapter.decoratorResults = decoratorResults;
    newAdapter.selectionEnabled = selectionEnabled;
    return newAdapter;
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

  protected abstract V createView(int position);

  protected abstract int indexOf(V view);

  protected abstract boolean isInstanceOfView(Object object);

  protected abstract DateRangeIndex createRangeIndex(CalendarDay min, CalendarDay max);

  @Override
  public int getItemPosition(@NonNull Object object) {
    if (!(isInstanceOfView(object))) {
      return POSITION_NONE;
    }
    CalendarPagerView pagerView = (CalendarPagerView) object;
    CalendarDay firstViewDay = pagerView.getFirstViewDay();
    if (firstViewDay == null) {
      return POSITION_NONE;
    }
    int index = indexOf((V) object);
    if (index < 0) {
      return POSITION_NONE;
    }
    return index;
  }

  @NonNull
  @Override
  public Object instantiateItem(@NonNull ViewGroup container, int position) {
    V pagerView = createView(position);
    pagerView.setContentDescription(mcv.getCalendarContentDescription());
    pagerView.setAlpha(0);
    pagerView.setSelectionEnabled(selectionEnabled);

    pagerView.setWeekDayFormatter(weekDayFormatter);
    pagerView.setDayFormatter(dayFormatter);
    pagerView.setDayFormatterContentDescription(dayFormatterContentDescription);
    if (color != null) {
      pagerView.setSelectionColor(color);
    }
    if (dateTextAppearance != null) {
      pagerView.setDateTextAppearance(dateTextAppearance);
    }
    if (weekDayTextAppearance != null) {
      pagerView.setWeekDayTextAppearance(weekDayTextAppearance);
    }
    pagerView.setShowOtherDates(showOtherDates);
    pagerView.setMinimumDate(minDate);
    pagerView.setMaximumDate(maxDate);
    pagerView.setSelectedDates(selectedDates);

    container.addView(pagerView);
    currentViews.add(pagerView);

    pagerView.setDayViewDecorators(decoratorResults);

    return pagerView;
  }

  public void setShowWeekDays(boolean showWeekDays) {
    this.showWeekDays = showWeekDays;
  }

  public boolean isShowWeekDays() {
    return showWeekDays;
  }

  public void setSelectionEnabled(boolean enabled) {
    selectionEnabled = enabled;
    for (V pagerView : currentViews) {
      pagerView.setSelectionEnabled(selectionEnabled);
    }
  }

  @Override
  public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    V pagerView = (V) object;
    currentViews.remove(pagerView);
    container.removeView(pagerView);
  }

  @Override
  public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
    return view == object;
  }

  public void setTitleFormatter(@Nullable TitleFormatter titleFormatter) {
    this.titleFormatter = titleFormatter == null ? TitleFormatter.DEFAULT : titleFormatter;
  }

  public void setSelectionColor(int color) {
    this.color = color;
    for (V pagerView : currentViews) {
      pagerView.setSelectionColor(color);
    }
  }

  public void setDateTextAppearance(int taId) {
    if (taId == 0) {
      return;
    }
    this.dateTextAppearance = taId;
    for (V pagerView : currentViews) {
      pagerView.setDateTextAppearance(taId);
    }
  }

  public void setShowOtherDates(@ShowOtherDates int showFlags) {
    this.showOtherDates = showFlags;
    for (V pagerView : currentViews) {
      pagerView.setShowOtherDates(showFlags);
    }
  }

  public void setWeekDayFormatter(WeekDayFormatter formatter) {
    this.weekDayFormatter = formatter;
    for (V pagerView : currentViews) {
      pagerView.setWeekDayFormatter(formatter);
    }
  }

  public void setDayFormatter(DayFormatter formatter) {
    dayFormatterContentDescription = dayFormatterContentDescription == dayFormatter ?
                                     formatter : dayFormatterContentDescription;
    this.dayFormatter = formatter;
    for (V pagerView : currentViews) {
      pagerView.setDayFormatter(formatter);
    }
  }

  public void setDayFormatterContentDescription(DayFormatter formatter) {
    dayFormatterContentDescription = formatter;
    for (V pagerView : currentViews) {
      pagerView.setDayFormatterContentDescription(formatter);
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
    for (V pagerView : currentViews) {
      pagerView.setWeekDayTextAppearance(taId);
    }
  }

  public void setRangeDates(CalendarDay min, CalendarDay max) {
    this.minDate = min;
    this.maxDate = max;
    for (V pagerView : currentViews) {
      pagerView.setMinimumDate(min);
      pagerView.setMaximumDate(max);
    }

    if (min == null) {
      min = CalendarDay.from(today.getYear() - 200, today.getMonth(), today.getDay());
    }

    if (max == null) {
      max = CalendarDay.from(today.getYear() + 200, today.getMonth(), today.getDay());
    }

    rangeIndex = createRangeIndex(min, max);

    notifyDataSetChanged();
    invalidateSelectedDates();
  }

  public DateRangeIndex getRangeIndex() {
    return rangeIndex;
  }

  public void clearSelections() {
    selectedDates.clear();
    invalidateSelectedDates();
  }

  /**
   * Select or un-select a day.
   *
   * @param day Day to select or un-select
   * @param selected Whether to select or un-select the day from the list.
   * @see CalendarPagerAdapter#selectRange(CalendarDay, CalendarDay)
   */
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

  /**
   * Clear the previous selection, select the range of days from first to last, and finally
   * invalidate. First day should be before last day, otherwise the selection won't happen.
   *
   * @param first The first day of the range.
   * @param last The last day in the range.
   * @see CalendarPagerAdapter#setDateSelected(CalendarDay, boolean)
   */
  public void selectRange(final CalendarDay first, final CalendarDay last) {
    selectedDates.clear();

    // Copy to start from the first day and increment
    LocalDate temp = LocalDate.of(first.getYear(), first.getMonth(), first.getDay());

    // for comparison
    final LocalDate end = last.getDate();

    while( temp.isBefore(end) || temp.equals(end) ) {
      selectedDates.add(CalendarDay.from(temp));
      temp = temp.plusDays(1);
    }

    invalidateSelectedDates();
  }

  private void invalidateSelectedDates() {
    validateSelectedDates();
    for (V pagerView : currentViews) {
      pagerView.setSelectedDates(selectedDates);
    }
  }

  private void validateSelectedDates() {
    for (int i = 0; i < selectedDates.size(); i++) {
      CalendarDay date = selectedDates.get(i);

      if ((minDate != null && minDate.isAfter(date)) || (maxDate != null
          && maxDate.isBefore(date))) {
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
}
