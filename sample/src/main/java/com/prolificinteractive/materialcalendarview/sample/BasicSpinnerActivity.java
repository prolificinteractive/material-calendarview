package com.prolificinteractive.materialcalendarview.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMonth;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author : hafiq on 12/04/2017.
 */

public class BasicSpinnerActivity extends AppCompatActivity implements OnDateSelectedListener, OnMonthChangedListener,AdapterView.OnItemSelectedListener {

    @Bind(R.id.spinner)
    Spinner spinner;
    @Bind(R.id.calendarView)
    MaterialCalendarView widget;
    @Bind(R.id.textView)
    TextView textView;

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_spinner);
        ButterKnife.bind(this);

        spinner.setOnItemSelectedListener(this);

        List<String> monthYear = new ArrayList<>();
        monthYear.add("Jan 2017");
        monthYear.add("Feb 2017");
        monthYear.add("Mac 2017");
        monthYear.add("Apr 2017");
        monthYear.add("May 2017");
        monthYear.add("Jun 2017");
        monthYear.add("July 2017");
        monthYear.add("Aug 2017");
        monthYear.add("Sep 2017");
        monthYear.add("Oct 2017");
        monthYear.add("Nov 2017");
        monthYear.add("Dec 2017");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, monthYear);
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, CalendarMonth.getList());

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @Nullable CalendarDay date, boolean selected) {
        textView.setText(getSelectedDatesString());
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        //noinspection ConstantConditions
        getSupportActionBar().setTitle(FORMATTER.format(date.getDate()));
    }

    private String getSelectedDatesString() {
        CalendarDay date = widget.getSelectedDate();
        if (date == null) {
            return "No Selection";
        }
        return FORMATTER.format(date.getDate());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        widget.setCurrentMonth(position);
//        widget.setCurrentMonth(CalendarMonth.valueOf(parent.getItemAtPosition(position).toString()),2017);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
