package com.prolificinteractive.materialcalendarview.sample.decorators;

import android.text.style.UnderlineSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Date;

/**
 * Decorate a day with a symbol in front of text
 */
public class OneDayDecorator implements DayViewDecorator {

    Date date;

    public OneDayDecorator() {
        date = new Date();
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(new CalendarDay(date));
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new UnderlineSpan());
    }

    /**
     * We're changing the internals, so make sure to call {@linkplain MaterialCalendarView#invalidateDecorators()}
     */
    public void setDate(Date date) {
        this.date = date;
    }
}
