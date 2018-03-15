package com.prolificinteractive.materialcalendarview.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomizeCodeActivity extends AppCompatActivity {

    @BindView(R.id.calendarView)
    MaterialCalendarView widget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        ButterKnife.bind(this);

        widget.setShowOtherDates(MaterialCalendarView.SHOW_ALL);
        widget.setArrowColor(getResources().getColor(R.color.sample_primary));
        widget.setLeftArrowMask(getResources().getDrawable(R.drawable.ic_navigation_arrow_back));
        widget.setRightArrowMask(getResources().getDrawable(R.drawable.ic_navigation_arrow_forward));
        widget.setSelectionColor(getResources().getColor(R.color.sample_primary));
        widget.setHeaderTextAppearance(R.style.TextAppearance_AppCompat_Medium);
        widget.setWeekDayTextAppearance(R.style.TextAppearance_AppCompat_Medium);
        widget.setDateTextAppearance(R.style.CustomDayTextAppearance);
        widget.setTitleFormatter(new MonthArrayTitleFormatter(getResources().getTextArray(R.array.custom_months)));
        widget.setWeekDayFormatter(new ArrayWeekDayFormatter(getResources().getTextArray(R.array.custom_weekdays)));
        widget.setTileSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 36, getResources().getDisplayMetrics()));
        widget.setTitleAnimationOrientation(MaterialCalendarView.VERTICAL);
        widget.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);

        CalendarDay today = CalendarDay.from(2016, 5, 2);
        widget.setCurrentDate(today);
        widget.setSelectedDate(today);
        widget.setDateSelected(CalendarDay.from(2016, 5, 3), true);

        widget.clearOldSelectionWhenStateIsNone(false);
        widget.setSelectionMode(MaterialCalendarView.SELECTION_MODE_NONE);

        widget.state().edit()
                .setFirstDayOfWeek(Calendar.WEDNESDAY)
                .setMinimumDate(CalendarDay.from(2016, 4, 3))
                .setMaximumDate(CalendarDay.from(2016, 5, 12))
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();

    }

}
