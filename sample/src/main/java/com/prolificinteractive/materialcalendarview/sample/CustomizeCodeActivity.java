package com.prolificinteractive.materialcalendarview.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateChangedListener;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CustomizeCodeActivity extends AppCompatActivity implements OnDateChangedListener {

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();

    @Bind(R.id.calendarView) MaterialCalendarView widget;
    @Bind(R.id.textView) TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        ButterKnife.bind(this);

        widget.setOnDateChangedListener(this);

        widget.setShowOtherDates(true);
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
        widget.setFirstDayOfWeek(Calendar.THURSDAY);
    }

    @Override
    public void onDateChanged(@NonNull MaterialCalendarView widget, CalendarDay date) {
        textView.setText(FORMATTER.format(date.getDate()));
    }

}
