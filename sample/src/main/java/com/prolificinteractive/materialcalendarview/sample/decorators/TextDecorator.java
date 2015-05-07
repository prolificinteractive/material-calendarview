package com.prolificinteractive.materialcalendarview.sample.decorators;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Collection;

/**
 * Decorate several days with a symbol
 */
public class TextDecorator implements DayViewDecorator {

    private int color;
    private Collection<CalendarDay> dates;

    public TextDecorator(int color, Collection<CalendarDay> dates) {
        this.color = color;
        this.dates = dates;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        for(CalendarDay date : dates)
            if(day.equals(date)){
                return true;
            }
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(5, color));
    }
}
