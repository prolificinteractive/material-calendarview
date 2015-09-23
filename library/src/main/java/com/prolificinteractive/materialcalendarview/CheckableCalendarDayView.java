package com.prolificinteractive.materialcalendarview;

import com.prolificinteractive.materialcalendarview.format.DayFormatter;

/**
 * Created by Szabo Laszlo on 9/16/2015
 * Edited by
 */
public interface CheckableCalendarDayView {

    CalendarDay getDate();

    void setSelectionColor(int color);

    void setDay(CalendarDay date);

    void setDayFormatter(DayFormatter formatter);

    void setupSelection(boolean showOtherDates, boolean inRange, boolean inMonth);

    void setChecked(boolean checked);

    void applyFacade(DayViewFacade facade);

}
