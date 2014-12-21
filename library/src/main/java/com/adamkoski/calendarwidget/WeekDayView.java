package com.adamkoski.calendarwidget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.SHORT;

/**
 * Display one day
 */
class WeekDayView extends TextView {

    public WeekDayView(Context context) {
        super(context);
        init();
    }

    public WeekDayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WeekDayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WeekDayView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setGravity(Gravity.CENTER);
        if(isInEditMode()) {
            setText("Mon");
        }

        int dp40 = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics()
        );
        setMinimumWidth(dp40);
        setMinimumHeight(dp40);
    }

    public void setDayOfWeek(int dayOfWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(DAY_OF_WEEK, dayOfWeek);
        String name = calendar.getDisplayName(DAY_OF_WEEK, SHORT, Locale.getDefault());
        setText(name);
    }
}
