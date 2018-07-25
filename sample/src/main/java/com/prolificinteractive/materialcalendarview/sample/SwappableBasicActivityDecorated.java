package com.prolificinteractive.materialcalendarview.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.sample.decorators.HighlightWeekendsDecorator;
import com.prolificinteractive.materialcalendarview.sample.decorators.MySelectorDecorator;
import com.prolificinteractive.materialcalendarview.sample.decorators.OneDayDecorator;
import org.threeten.bp.LocalDate;
import org.threeten.bp.Month;

/**
 * Shows off the most basic usage
 */
public class SwappableBasicActivityDecorated extends AppCompatActivity
    implements OnDateSelectedListener {

  private final OneDayDecorator oneDayDecorator = new OneDayDecorator();

  @BindView(R.id.calendarView) MaterialCalendarView widget;

  @Override protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_basic_modes);
    ButterKnife.bind(this);

    widget.setOnDateChangedListener(this);
    widget.setShowOtherDates(MaterialCalendarView.SHOW_ALL);

    final LocalDate instance = LocalDate.now();
    widget.setSelectedDate(instance);

    final LocalDate min = LocalDate.of(instance.getYear(), Month.JANUARY, 1);
    final LocalDate max = LocalDate.of(instance.getYear(), Month.DECEMBER, 31);

    widget.state().edit().setMinimumDate(min).setMaximumDate(max).commit();

    widget.addDecorators(
        new MySelectorDecorator(this),
        new HighlightWeekendsDecorator(),
        oneDayDecorator
    );
  }

  @Override
  public void onDateSelected(
      @NonNull MaterialCalendarView widget,
      @NonNull CalendarDay date,
      boolean selected) {
    //If you change a decorate, you need to invalidate decorators
    oneDayDecorator.setDate(date.getDate());
    widget.invalidateDecorators();
  }

  @OnClick(R.id.button_weeks)
  public void onSetWeekMode() {
    widget.state().edit()
        .setCalendarDisplayMode(CalendarMode.WEEKS)
        .commit();
  }

  @OnClick(R.id.button_months)
  public void onSetMonthMode() {
    widget.state().edit()
        .setCalendarDisplayMode(CalendarMode.MONTHS)
        .commit();
  }
}
