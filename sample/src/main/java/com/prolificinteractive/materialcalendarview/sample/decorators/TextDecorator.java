package com.prolificinteractive.materialcalendarview.sample.decorators;

import android.graphics.Color;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.SymbolSpan;

import java.util.Collection;
import java.util.Date;

/**
 * Decorate several days with a symbol
 */
public class TextDecorator implements DayViewDecorator {

    Collection<? extends Date> dates;
    String symbol;

    public TextDecorator(String symbol, Collection<? extends Date> dates) {
        this.symbol = symbol;
        this.dates = dates;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        for(Date date : dates)
            if(day.equals(new CalendarDay(date))){
                return true;
            }
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new SymbolSpan(symbol, Color.parseColor("#CE93D8")));
    }
}
