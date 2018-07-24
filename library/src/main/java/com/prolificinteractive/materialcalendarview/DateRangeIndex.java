package com.prolificinteractive.materialcalendarview;

/**
 * Use math to calculate first days of months or weeks by position from a minimum date (and first
 * day of week in case of weekly range).
 */
interface DateRangeIndex {

  /**
   * Count of pages displayed between 2 dates.
   */
  int getCount();

  /**
   * Index of the page where the date is displayed.
   */
  int indexOf(CalendarDay day);

  /**
   * Get the first date at the position within the index.
   */
  CalendarDay getItem(int position);
}
