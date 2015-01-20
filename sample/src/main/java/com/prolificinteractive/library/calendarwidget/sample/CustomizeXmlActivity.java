package com.prolificinteractive.library.calendarwidget.sample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.prolificinteractive.library.calendarwidget.CalendarDay;
import com.prolificinteractive.library.calendarwidget.CalendarWidget;
import com.prolificinteractive.library.calendarwidget.OnDateChangedListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CustomizeXmlActivity extends ActionBarActivity implements OnDateChangedListener {

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customization);

        textView = (TextView) findViewById(R.id.textView);

        CalendarWidget widget = (CalendarWidget) findViewById(R.id.calendarView);
        widget.setOnDateChangedListener(this);
    }

    @Override
    public void onDateChanged(CalendarWidget widget, CalendarDay date) {
        textView.setText(FORMATTER.format(date.getDate()));
    }

}
