package com.prolificinteractive.materialcalendarview.sample.decorators;

import android.text.SpannableStringBuilder;

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
    CharSequence symbol;

    public OneDayDecorator(CharSequence symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(CalendarDay.from(date));
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setText(
            new SpannableStringBuilder().append(symbol).append(" ").append(view.getText())
        );
    }

    /**
     * We're changing the internals, so make sure to call {@linkplain MaterialCalendarView#invalidateDecorators()}
     */
    public void setDate(Date date) {
        this.date = date;
    }
}
