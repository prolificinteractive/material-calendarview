package com.prolificinteractive.materialcalendarview;

import androidx.annotation.NonNull;
import org.threeten.bp.Period;

/**
 * Pager adapter backing the calendar view
 */
class MonthPagerAdapter extends CalendarPagerAdapter<MonthView> {

  MonthPagerAdapter(MaterialCalendarView mcv) {
    super(mcv);
  }

  @Override protected MonthView createView(int position) {
    return new MonthView(mcv, getItem(position), mcv.getFirstDayOfWeek(), showWeekDays);
  }

  @Override protected int indexOf(MonthView view) {
    CalendarDay month = view.getMonth();
    return getRangeIndex().indexOf(month);
  }

  @Override protected boolean isInstanceOfView(Object object) {
    return object instanceof MonthView;
  }

  @Override protected DateRangeIndex createRangeIndex(CalendarDay min, CalendarDay max) {
    return new Monthly(min, max);
  }

  public static class Monthly implements DateRangeIndex {

    /**
     * Minimum date with the first month to display.
     */
    private final CalendarDay min;

    /**
     * Number of months to display.
     */
    private final int count;

    public Monthly(@NonNull final CalendarDay min, @NonNull final CalendarDay max) {
      this.min = CalendarDay.from(min.getYear(), min.getMonth(), 1);
      this.count = indexOf(max) + 1;
    }

    @Override public int getCount() {
      return count;
    }

    @Override public int indexOf(final CalendarDay day) {
      return (int) Period
          .between(min.getDate().withDayOfMonth(1), day.getDate().withDayOfMonth(1))
          .toTotalMonths();
    }

    @Override public CalendarDay getItem(final int position) {
      return CalendarDay.from(min.getDate().plusMonths(position));
    }
  }
}
