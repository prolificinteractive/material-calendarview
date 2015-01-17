package com.prolificinteractive.library.calendarwidget.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.prolificinteractive.library.calendarwidget.CalendarDay;
import com.prolificinteractive.library.calendarwidget.CalendarWidget;
import com.prolificinteractive.library.calendarwidget.OnDateChangedListener;

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
        //widget.setSelectedDate(calendar);

        calendar.set(2014, Calendar.JULY, 31);
        widget.setMinimumDate(calendar);

        calendar.set(2015, Calendar.MAY, 4);
        widget.setMaximumDate(calendar);
    }

    @Override
    public void onDateChanged(CalendarWidget widget, CalendarDay date) {
        Toast.makeText(this, formatter.format(date.getDate()), Toast.LENGTH_SHORT).show();
    }
}
