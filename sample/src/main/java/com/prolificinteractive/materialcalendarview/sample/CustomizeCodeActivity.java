package com.prolificinteractive.materialcalendarview.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;
import org.threeten.bp.DayOfWeek;

public class CustomizeCodeActivity extends AppCompatActivity {

  @BindView(R.id.calendarView)
  MaterialCalendarView widget;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_basic);
    ButterKnife.bind(this);

    widget.setShowOtherDates(MaterialCalendarView.SHOW_ALL);
    widget.setLeftArrow(R.drawable.ic_arrow_back);
    widget.setRightArrow(R.drawable.ic_arrow_forward);
    widget.setSelectionColor(getResources().getColor(R.color.sample_primary));
    widget.setWeekDayTextAppearance(R.style.CustomTextAppearance);
    widget.setHeaderTextAppearance(R.style.CustomTextAppearance);
    widget.setDateTextAppearance(R.style.CustomTextAppearance);
    widget.setTitleFormatter(new MonthArrayTitleFormatter(getResources().getTextArray(R.array.custom_months)));
    widget.setWeekDayFormatter(new ArrayWeekDayFormatter(getResources().getTextArray(R.array.custom_weekdays)));
    widget.setTileSize((int) TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        36,
        getResources().getDisplayMetrics()
    ));
    widget.setTitleAnimationOrientation(MaterialCalendarView.VERTICAL);

    CalendarDay today = CalendarDay.from(2016, 5, 2);
    widget.setCurrentDate(today);
    widget.setSelectedDate(today);

    widget.state().edit()
        .setFirstDayOfWeek(DayOfWeek.WEDNESDAY)
        .setMinimumDate(CalendarDay.from(2016, 4, 3))
        .setMaximumDate(CalendarDay.from(2016, 5, 12))
        .setCalendarDisplayMode(CalendarMode.WEEKS)
        .commit();
  }
}
