package com.prolificinteractive.materialcalendarview.sample.decorators;

import android.content.Context;
import android.graphics.Color;
import android.text.style.RelativeSizeSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.spans.OneDayUniqueSpan;

import java.util.Date;
import java.util.HashMap;

/**
 * Decorate a day by making the text big and bold
 */
public class OneDayUniqueDecorator implements DayViewDecorator {

    private final Context context;
    private CalendarDay date;
    private HashMap<CalendarDay, String> map;
    private String selectedUniqueString;
    private int colorDate, colorString;

    public OneDayUniqueDecorator(Context context, int selectedDateColor, int selectedUniqueColor) {
        this.context = context;
        this.colorDate = selectedDateColor;
        this.colorString = selectedUniqueColor;
        date = CalendarDay.today();
    }

    public void setMap(HashMap<CalendarDay, String> map) {
        this.map = map;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new RelativeSizeSpan(0f));
        view.addSpan(new OneDayUniqueSpan(context, colorDate, colorString, selectedUniqueString));
    }

    /**
     * We're changing the internals, so make sure to call {@linkplain MaterialCalendarView#invalidateDecorators()}
     */
    public void setDate(Date date) {
        this.date = CalendarDay.from(date);
        if (map != null && map.get(this.date) != null)
            selectedUniqueString = this.date.getDay() + "\n" + map.get(this.date);
        else if (map != null)
            selectedUniqueString = String.valueOf(this.date.getDay());
    }
}