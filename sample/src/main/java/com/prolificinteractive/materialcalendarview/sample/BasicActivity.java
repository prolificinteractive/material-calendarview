package com.prolificinteractive.materialcalendarview.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateChangedListener;
import com.prolificinteractive.materialcalendarview.format.DateFormatTitleFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Shows off the most basic usage
 */
public class BasicActivity extends ActionBarActivity implements OnDateChangedListener {

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        textView = (TextView) findViewById(R.id.textView);

        MaterialCalendarView widget = (MaterialCalendarView) findViewById(R.id.calendarView);
        widget.setOnDateChangedListener(this);
        widget.setTitleFormatter(new DateFormatTitleFormatter(SimpleDateFormat.getDateInstance()));
//        widget.setTitleFormatter(new MonthArrayTitleFormatter(new CharSequence[] {
//                "Jan", "Feb", "Mar", "Apr", "May", "Jun", "JULY!!!", "Aug", "Sept", "OCt", "NOV", "DEC"
//        }));
    }

    @Override
    public void onDateChanged(MaterialCalendarView widget, CalendarDay date) {
        textView.setText(FORMATTER.format(date.getDate()));
    }
}
