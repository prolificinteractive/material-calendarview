package com.prolificinteractive.materialcalendarview.listeners;

import android.support.annotation.NonNull;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

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
    fun onDateSelected(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean);
}
