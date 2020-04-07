package com.prolificinteractive.materialcalendarview;

import android.graphics.Color;
import android.text.style.ForegroundColorSpan;

public class NoSelectionDecorator implements DayViewDecorator {

    public NoSelectionDecorator() {
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(Color.BLACK));

    }
}
