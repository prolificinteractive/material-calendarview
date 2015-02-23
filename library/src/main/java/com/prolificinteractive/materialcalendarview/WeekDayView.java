package com.prolificinteractive.materialcalendarview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Display a day of the week
 */
class WeekDayView extends TextView {

    public WeekDayView(Context context) {
        this(context, null);
    }

    public WeekDayView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setGravity(Gravity.CENTER);

        if(isInEditMode()) {
            setText("Mon");
        }
    }

    public void setDayOfWeek(int dayOfWeek) {
        String name = CalendarHelper.getDayOfWeekDisplayName(dayOfWeek);
        setText(name);
    }
}
