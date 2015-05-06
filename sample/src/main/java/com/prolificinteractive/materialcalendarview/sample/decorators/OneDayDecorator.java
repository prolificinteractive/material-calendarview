package com.prolificinteractive.materialcalendarview.sample.decorators;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.spans.SymbolSpan;

import java.util.Date;

/**
 * Decorate a day with a symbol in front of text
 */
public class OneDayDecorator implements DayViewDecorator {

    Date date;
    String symbol;

    public OneDayDecorator(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(new CalendarDay(date));
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new SymbolSpan(symbol));
    }

    /**
     * We're changing the internals, so make sure to call {@linkplain MaterialCalendarView#invalidateDecorators()}
     */
    public void setDate(Date date) {
        this.date = date;
    }
}
