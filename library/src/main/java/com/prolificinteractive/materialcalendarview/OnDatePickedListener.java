package com.prolificinteractive.materialcalendarview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface OnDatePickedListener {

    /**
     * Called when a user clicks on a day cell.
     * There is no logic to prevent multiple calls for the same date and state.
     *
     * @param widget      the view associated with this listener
     * @param clickedCell the cell clicked. if selected is false the clickedCell might  be null
     * @param date        the date that was selected or unselected
     * @param selected    true if the day is now selected, false otherwise
     */
    void onDatePicked(@NonNull MaterialCalendarView widget,
                      @Nullable DayView clickedCell,
                      @NonNull CalendarDay date,
                      boolean selected);

}
