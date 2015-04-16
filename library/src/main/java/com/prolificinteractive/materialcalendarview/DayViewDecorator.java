package com.prolificinteractive.materialcalendarview;

import android.content.Context;

/**
 * Created by castrelo on 11/04/15.
 */
public interface DayViewDecorator {

    public boolean shouldDecorate(CalendarDay day);

    public void decorate(DayView view);

}
