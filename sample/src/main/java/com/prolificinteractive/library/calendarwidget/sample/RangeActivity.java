package com.prolificinteractive.library.calendarwidget.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import com.prolificinteractive.library.calendarwidget.CalendarDay;
import com.prolificinteractive.library.calendarwidget.CalendarWidget;
import com.prolificinteractive.library.calendarwidget.OnDateChangedListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Shows off the most basic usage
 */
public class RangeActivity extends ActionBarActivity implements OnDateChangedListener {

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        textView = (TextView) findViewById(R.id.textView);

        CalendarWidget widget = (CalendarWidget) findViewById(R.id.calendarView);
        widget.setOnDateChangedListener(this);

        Calendar calendar = Calendar.getInstance();
        widget.setSelectedDate(calendar);

        calendar.set(calendar.get(Calendar.YEAR), Calendar.JANUARY, 1);
        widget.setMinimumDate(calendar);

        calendar.set(calendar.get(Calendar.YEAR) + 2, Calendar.OCTOBER, 31);
        widget.setMaximumDate(calendar);
    }

    @Override
    public void onDateChanged(CalendarWidget widget, CalendarDay date) {
        textView.setText(FORMATTER.format(date.getDate()));
    }
}
