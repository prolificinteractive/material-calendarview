package com.prolificinteractive.materialcalendarview.listeners;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

/**
 * The callback used to indicate the user changes the displayed month
 */
public interface OnMonthChangedListener {

    /**
     * Called upon change of the selected day
     *
     * @param widget the view associated with this listener
     * @param date the month picked, as the first day of the month
     */
    fun onMonthChanged(widget: MaterialCalendarView, date: CalendarDay);
}
