package com.adamkoski.calendarwidget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.adamkoski.library.calendarwidget.R;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Display one day
 */
class DayView extends TextView {

    public DayView(Context context) {
        super(context);
        init();
    }

    public DayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DayView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setBackgroundResource(R.drawable.cw__indicator_normal);
        setGravity(Gravity.CENTER);
        if(isInEditMode()) {
            setText("99");
        }
    }

    public void setDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        setText(String.valueOf(calendar.get(Calendar.DATE)));
    }
}
