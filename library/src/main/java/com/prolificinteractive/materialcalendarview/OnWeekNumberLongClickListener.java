package com.prolificinteractive.materialcalendarview;

import android.support.annotation.NonNull;

/**
 * The callback used to indicate a week number has been long clicked.
 */
public interface OnWeekNumberLongClickListener {

    /**
     * Called when a user long clicks on a week number.
     * There is no logic to prevent multiple calls for the same date and state.
     *
     * @param widget the view associated with this listener
     * @param date   the first day of the week, of the week number that was long clicked
     */
    void onWeekNumberLongClick(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date);
}
