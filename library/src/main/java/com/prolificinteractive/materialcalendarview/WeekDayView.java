package com.prolificinteractive.materialcalendarview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.SHORT;

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
        Calendar calendar = CalendarUtils.getInstance();
        calendar.set(DAY_OF_WEEK, dayOfWeek);
        String name = calendar.getDisplayName(DAY_OF_WEEK, SHORT, Locale.getDefault());
        setText(name);
    }
}
