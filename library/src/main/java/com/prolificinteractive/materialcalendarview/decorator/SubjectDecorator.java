package com.prolificinteractive.materialcalendarview.decorator;

import android.content.Context;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

public class SubjectDecorator implements DayViewDecorator {
    private CalendarDay calendarDay;
    private int[] color;
    private Context context;

    public SubjectDecorator(CalendarDay calendarDay, int[] color, Context context) {
        this.calendarDay = calendarDay;
        this.color = color;
        this.context = context;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return calendarDay.equals(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new CustomDecorator(color, context));
    }
}
