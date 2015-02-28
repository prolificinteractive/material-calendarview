package com.prolificinteractive.materialcalendarview.format;

import com.prolificinteractive.materialcalendarview.CalendarDay;

/**
 * Used to format a {@linkplain com.prolificinteractive.materialcalendarview.CalendarDay} to a string for the month/year title
*/
public interface TitleFormatter {
    CharSequence format(CalendarDay day);
}
