package com.prolificinteractive.materialcalendarview;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.style.ForegroundColorSpan;

import androidx.core.content.ContextCompat;


public class CurrentDayDecorator implements DayViewDecorator {

    private final Drawable selectionDrawable;
    private Drawable drawable;
    private boolean flag;

    private CalendarDay currentDay = CalendarDay.today();

    CurrentDayDecorator(Activity context, boolean flag) {
        drawable = ContextCompat.getDrawable(context, R.drawable.gray_circle);
        selectionDrawable = ContextCompat.getDrawable(context, R.drawable.calendar_selector);
        this.flag = flag;

    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return day.equals(currentDay);
    }

    @Override
    public void decorate(DayViewFacade view) {
        if (flag == true) {
            view.addSpan(new ForegroundColorSpan(Color.WHITE));
            view.setSelectionDrawable(selectionDrawable);

        } else {
            view.addSpan(new ForegroundColorSpan(Color.BLACK));
            view.setSelectionDrawable(drawable);

        }
    }
}