package com.prolificinteractive.materialcalendarview;

/**
 * The callback used to indicate the user changes the displayed week
 */
public interface OnWeekChangedListener {

  /**
   * Called upon change of the selected day
   *
   * @param widget the view associated with this listener
   * @param date the week picked, as the first day of the week
   */
  void onWeekChanged(MaterialCalendarView widget, CalendarDay date);
}
