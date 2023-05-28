package com.prolificinteractive.materialcalendarview.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import org.threeten.bp.LocalDate;
import org.threeten.bp.Month;

/**
 * Show off setting min and max dates and disabling individual days
 */
public class DisableDaysActivity extends AppCompatActivity {

    @BindView(R.id.calendarView)
    MaterialCalendarView widget;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        ButterKnife.bind(this);
        // Add a decorator to disable prime numbered days
        widget.addDecorator(new PrimeDayDisableDecorator());
        // Add a second decorator that explicitly enables days <= 10. This will work because
        // decorators are applied in order, and the system allows re-enabling
        widget.addDecorator(new EnableOneToTenDecorator());
        final LocalDate calendar = LocalDate.now();
        widget.setSelectedDate(calendar);
        final LocalDate min = LocalDate.of(calendar.getYear(), Month.JANUARY, 1);
        final LocalDate max = LocalDate.of(calendar.getYear() + 1, Month.OCTOBER, 31);
        widget.state().edit().setMinimumDate(min).setMaximumDate(max).commit();
    }

    private static class PrimeDayDisableDecorator implements DayViewDecorator {

        @Override
        public boolean shouldDecorate(final CalendarDay day) {
            return PRIME_TABLE[day.getDay()];
        }

        @Override
        public void decorate(final DayViewFacade view) {
            view.setDaysDisabled(true);
        }

        private static boolean[] PRIME_TABLE = { // 0?
        false, false, // 2
        true, // 3
        true, false, // 5
        true, false, // 7
        true, false, false, false, // 11
        true, false, // 13
        true, false, false, false, // 17
        true, false, // 19
        true, false, false, false, // 23
        true, false, false, false, false, false, // 29
        true, false, // 31
        true, false, false, //PADDING
        false };
    }

    private static class EnableOneToTenDecorator implements DayViewDecorator {

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return day.getDay() <= 10;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setDaysDisabled(false);
        }
    }
}
