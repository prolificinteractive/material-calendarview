package com.adamkoski.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.adamkoski.calendarwidget.CalendarDay;
import com.adamkoski.calendarwidget.CalendarWidget;
import com.adamkoski.calendarwidget.OnDateChangedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends ActionBarActivity implements OnDateChangedListener {

    private static final DateFormat formatter = SimpleDateFormat.getDateInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarWidget widget = (CalendarWidget) findViewById(R.id.calendarView);
        widget.setOnDateChangedListener(this);

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DATE, -100);
        widget.setMinimumDate(calendar);

        calendar.add(Calendar.DATE, 100);
        widget.setSelectedDate(calendar);

        calendar.add(Calendar.DATE, 100);
        widget.setMaximumDate(calendar);
    }

    @Override
    public void onDateChanged(CalendarWidget widget, CalendarDay date) {
        Toast.makeText(this, formatter.format(date.getDate()), Toast.LENGTH_SHORT).show();
    }
}
