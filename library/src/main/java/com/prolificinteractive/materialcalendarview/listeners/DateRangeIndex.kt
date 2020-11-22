package com.prolificinteractive.materialcalendarview.listeners;

import com.prolificinteractive.materialcalendarview.CalendarDay;

/**
 * Use math to calculate first days of months or weeks by position from a minimum date (and first
 * day of week in case of weekly range).
 */
public interface DateRangeIndex {

    /**
     * Count of pages displayed between 2 dates.
     */
    fun getCount(): Int;

    /**
     * Index of the page where the date is displayed.
     */
    fun indexOf(day: CalendarDay): Int;

    /**
     * Get the first date at the position within the index.
     */
    fun getItem(position: Int): CalendarDay;
}
