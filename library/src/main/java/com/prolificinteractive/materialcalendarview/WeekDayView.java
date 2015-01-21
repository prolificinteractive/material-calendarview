package com.prolificinteractive.materialcalendarview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;
import com.prolificinteractive.library.calendarwidget.R;
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

        setTextAppearance(context, R.style.TextAppearance_MaterialCalendarWidget_WeekDay);

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
