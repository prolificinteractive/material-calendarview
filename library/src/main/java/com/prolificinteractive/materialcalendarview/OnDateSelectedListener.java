package com.prolificinteractive.materialcalendarview;

import androidx.annotation.NonNull;

/**
 * The callback used to indicate a date has been selected or deselected
 */
public interface OnDateSelectedListener {

  /**
   * Called when a user clicks on a day.
   * There is no logic to prevent multiple calls for the same date and state.
   *
   * @param widget the view associated with this listener
   * @param date the date that was selected or unselected
   * @param selected true if the day is now selected, false otherwise
   */
  void onDateSelected(
      @NonNull MaterialCalendarView widget,
      @NonNull CalendarDay date,
      boolean selected);
}
