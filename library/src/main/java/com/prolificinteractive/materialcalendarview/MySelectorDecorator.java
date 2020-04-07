package com.prolificinteractive.materialcalendarview;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.style.ForegroundColorSpan;

import java.util.Collection;
import java.util.HashSet;

public class MySelectorDecorator implements DayViewDecorator {

    private final Drawable drawable;
    private HashSet<CalendarDay> dates;


    MySelectorDecorator(Activity context, Collection<CalendarDay> dates, int Drawable) {
        drawable = context.getResources().getDrawable(Drawable);
        this.dates = new HashSet<>(dates);
    }

    MySelectorDecorator(Activity context, int Drawable) {
        drawable = context.getResources().getDrawable(Drawable);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates == null ? true : dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
        if (dates != null) {
            view.addSpan(new ForegroundColorSpan(Color.WHITE));
        }
    }
}



