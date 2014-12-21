package com.adamkoski.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.adamkoski.calendarwidget.CalendarDay;
import com.adamkoski.calendarwidget.CalendarWidget;
import com.adamkoski.calendarwidget.OnDateChangedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class MainActivity extends ActionBarActivity implements OnDateChangedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarWidget widget = (CalendarWidget) findViewById(R.id.calendarView);
        widget.setOnDateChangedListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    static final DateFormat formatter = SimpleDateFormat.getDateInstance();

    @Override
    public void onDateChanged(CalendarWidget widget, CalendarDay date) {
        Toast.makeText(this, formatter.format(date.getDate()), Toast.LENGTH_SHORT).show();
    }
}
