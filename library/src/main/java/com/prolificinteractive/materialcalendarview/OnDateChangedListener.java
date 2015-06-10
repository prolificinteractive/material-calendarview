package com.prolificinteractive.materialcalendarview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * The callback used to indicate the user changes the date
 */
public interface OnDateChangedListener {

    /**
     * Called upon change of the selected day
     *
     * @param widget the view associated with this listener
     * @param date   the new date. May be null if selection was cleared
     */
    public void onDateChanged(@NonNull MaterialCalendarView widget, @Nullable CalendarDay date);
}
