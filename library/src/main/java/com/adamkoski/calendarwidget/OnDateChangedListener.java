package com.adamkoski.calendarwidget;

import java.util.Calendar;

/**
 * Callback for date changes
 */
public interface OnDateChangedListener {

    public void onDateChanged(CalendarWidget widget, Calendar date);
}
