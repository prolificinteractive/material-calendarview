package com.prolificinteractive.materialcalendarview.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateChangedListener;
import com.prolificinteractive.materialcalendarview.sample.decorators.HighlightWeekendsDecorator;
import com.prolificinteractive.materialcalendarview.sample.decorators.OneDayDecorator;
import com.prolificinteractive.materialcalendarview.sample.decorators.TextDecorator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Shows off the most basic usage
 */
public class BasicActivityDecorated extends ActionBarActivity implements OnDateChangedListener {

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private TextView textView;
    private OneDayDecorator oneDayDecorator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        textView = (TextView) findViewById(R.id.textView);

        MaterialCalendarView widget = (MaterialCalendarView) findViewById(R.id.calendarView);
        widget.setOnDateChangedListener(this);


        Calendar calendar = Calendar.getInstance();
        widget.setSelectedDate(calendar.getTime());

        calendar.set(calendar.get(Calendar.YEAR), Calendar.JANUARY, 1);
        widget.setMinimumDate(calendar.getTime());

        calendar.set(calendar.get(Calendar.YEAR) + 2, Calendar.OCTOBER, 31);
        widget.setMaximumDate(calendar.getTime());

        Calendar d1 = Calendar.getInstance();
        d1.set(d1.get(Calendar.YEAR), Calendar.APRIL, 3, 0, 0, 0);

        Calendar d2 = Calendar.getInstance();
        d2.set(d2.get(Calendar.YEAR), Calendar.APRIL, 5, 0, 0, 0);

        Calendar d3 = Calendar.getInstance();
        d3.set(d3.get(Calendar.YEAR), Calendar.APRIL, 7, 0, 0, 0);

        Calendar d4 = Calendar.getInstance();
        d4.set(d4.get(Calendar.YEAR), Calendar.APRIL, 20, 0, 0, 0);

        Calendar d5 = Calendar.getInstance();
        d5.set(d5.get(Calendar.YEAR), Calendar.APRIL, 22, 0, 0, 0);

        widget.addDecorators(
                new HighlightWeekendsDecorator(),
                new TextDecorator("●", Arrays.asList(d1.getTime(), d2.getTime(), d3.getTime())),
                new TextDecorator("✔", Arrays.asList(d4.getTime(), d5.getTime()))
        );

    }

    @Override
    public void onDateChanged(MaterialCalendarView widget, CalendarDay date) {

        if(oneDayDecorator == null){
            oneDayDecorator = new OneDayDecorator("★",date.getDate());
            widget.addDecorators(oneDayDecorator);
        }else{
            oneDayDecorator.setDate(date.getDate());
            widget.invalidateDecorators();
        }


        textView.setText(FORMATTER.format(date.getDate()));
    }
}
