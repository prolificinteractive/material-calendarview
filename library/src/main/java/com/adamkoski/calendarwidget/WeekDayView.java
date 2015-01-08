package com.adamkoski.calendarwidget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import java.util.Locale;

import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.SHORT;

/**
 * Display one day
 */
@SuppressLint("ViewConstructor")
class WeekDayView extends TextView {

    public WeekDayView(Context context, int size) {
        super(context);

        setGravity(Gravity.CENTER);

        setMinimumWidth(size);
        setMinimumHeight(size);

        if(isInEditMode()) {
            setText("Mon");
        }
    }

    public void setDayOfWeek(int dayOfWeek) {
        CalendarWrapper calendar = CalendarWrapper.getInstance();
        calendar.set(DAY_OF_WEEK, dayOfWeek);
        String name = calendar.getDisplayName(DAY_OF_WEEK, SHORT, Locale.getDefault());
        setText(name);
    }
}
