package com.prolificinteractive.materialcalendarview.sample.decorators;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DoubleDotSpan;
import java.util.Collection;
import java.util.HashSet;

/**
 * Decorate several days with two dots
 */
public class DoubleEventDecorator implements DayViewDecorator {

    private int colorLeft;
    private int colorRight;
    private HashSet<CalendarDay> dates;

    public DoubleEventDecorator(int colorLeft, int colorRight,
        Collection<CalendarDay> dates) {
        this.colorLeft = colorLeft;
        this.colorRight = colorRight;
        this.dates = new HashSet<>(dates);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DoubleDotSpan(5, colorLeft, colorRight));
    }
}
