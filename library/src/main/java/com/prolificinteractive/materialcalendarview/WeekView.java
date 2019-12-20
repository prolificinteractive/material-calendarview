package com.prolificinteractive.materialcalendarview;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import java.util.Collection;
import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;

/**
 * Display a week of {@linkplain DayView}s and
 * seven {@linkplain WeekDayView}s.
 */
@SuppressLint("ViewConstructor") class WeekView extends CalendarPagerView {

  public WeekView(
      @NonNull final MaterialCalendarView view,
      final CalendarDay firstViewDay,
      final DayOfWeek firstDayOfWeek,
      final boolean showWeekDays) {
    super(view, firstViewDay, firstDayOfWeek, showWeekDays);
  }

  @Override protected void buildDayViews(
      final Collection<DayView> dayViews,
      final LocalDate calendar) {
    LocalDate temp = calendar;
    for (int i = 0; i < DEFAULT_DAYS_IN_WEEK; i++) {
      addDayView(dayViews, temp);
      temp = temp.plusDays(1);
    }
  }

  @Override protected boolean isDayEnabled(CalendarDay day) {
    return true;
  }

  @Override protected int getRows() {
    return showWeekDays ? DAY_NAMES_ROW + 1 : 1;
  }
}
