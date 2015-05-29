package com.prolificinteractive.materialcalendarview.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Shows off the most basic usage
 */
public class OldCalendarViewActivity extends AppCompatActivity
    implements CalendarView.OnDateChangeListener {

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_calendarview);

        textView = (TextView) findViewById(R.id.textView);

        CalendarView widget = (CalendarView) findViewById(R.id.calendarView);
        widget.setOnDateChangeListener(this);
    }

  @Override public void onSelectedDayChange(CalendarView view, int year, int month,
      int dayOfMonth) {
        textView.setText(FORMATTER.format(view.getDate()));
    }
}
