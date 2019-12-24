package com.prolificinteractive.materialcalendarview.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.threeten.bp.DayOfWeek;

import java.util.Calendar;

public class CalenderOnAppBarActivity extends AppCompatActivity {


    MaterialCalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_on_app_bar);
        Calendar minDay = Calendar.getInstance();
        minDay.add(Calendar.DAY_OF_MONTH, -(15));
        calendarView=findViewById(R.id.calenderView);
        calendarView.state().edit()
                .setFirstDayOfWeek(DayOfWeek.SUNDAY)
                .setMaximumDate(CalendarDay.today())
                .setMinimumDate(
                        CalendarDay.from(
                                minDay.get(Calendar.YEAR),
                                minDay.get(Calendar.MONTH) + 1,
                                minDay.get(Calendar.DAY_OF_MONTH)
                        )
                )
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();
    }
}
