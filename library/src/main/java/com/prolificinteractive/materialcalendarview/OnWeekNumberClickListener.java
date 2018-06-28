package com.prolificinteractive.materialcalendarview;

import android.support.annotation.NonNull;

/**
 * Callback to indicate a week number has been selected
 */
public interface OnWeekNumberClickListener {

    /**
     * Called when a user clicks on a Week Number, when week numbers are displayed
     * @param widget    The view associated with this listener
     * @param date      The first day of the week of the selected week
     */
    void onWeekNumberClick ( @NonNull MaterialCalendarView widget, @NonNull CalendarDay date );
}
