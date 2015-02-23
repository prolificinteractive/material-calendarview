package com.prolificinteractive.materialcalendarview;

/**
 * The callback used to indicate the user changes the date
 */
public interface OnDateChangedListener {

    /**
     * Called upon change of the selected day
     *
     * @param widget the view associated with this listener
     * @param date   the date picked
     */
    public void onDateChanged(MaterialCalendarView widget, long date);
}
