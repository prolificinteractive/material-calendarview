package com.prolificinteractive.materialcalendarview;

import android.support.annotation.NonNull;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;
import org.threeten.bp.jdk8.Jdk8Methods;
import org.threeten.bp.temporal.ChronoUnit;
import org.threeten.bp.temporal.WeekFields;

public class TwoWeekPagerAdapter extends CalendarPagerAdapter<TwoWeekView> {

    public TwoWeekPagerAdapter(MaterialCalendarView mcv) {
        super(mcv);
    }

    @Override
    protected TwoWeekView createView(int position) {
        return new TwoWeekView(mcv, getItem(position), mcv.getFirstDayOfWeek(), showWeekDays);
    }

    @Override
    protected int indexOf(TwoWeekView view) {
        CalendarDay week = view.getFirstViewDay();
        return getRangeIndex().indexOf(week);
    }

    @Override
    protected boolean isInstanceOfView(Object object) {
        return object instanceof TwoWeekView;
    }

    @Override
    protected DateRangeIndex createRangeIndex(CalendarDay min, CalendarDay max) {
        return new BiWeekly(min, max, mcv.getFirstDayOfWeek());
    }

    public static class BiWeekly implements DateRangeIndex {

        private static final int DAYS_IN_TWO_WEEK = 14;
        private final CalendarDay min;
        private final int count;
        /**
         * First day of the week to base the weeks on.
         */
        private final DayOfWeek firstDayOfWeek;

        public BiWeekly(@NonNull CalendarDay min, @NonNull CalendarDay max, DayOfWeek firstDayOfWeek) {
            this.firstDayOfWeek = firstDayOfWeek;
            this.min = getFirstDayOfWeek(min);
            this.count = indexOf(max) + 1;
        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public int indexOf(CalendarDay day) {
            final WeekFields weekFields = WeekFields.of(firstDayOfWeek, 1);
            final LocalDate temp = day.getDate().with(weekFields.dayOfWeek(), 1L);
            return (int) ChronoUnit.DAYS.between(min.getDate(), temp)/DAYS_IN_TWO_WEEK;
        }

        @Override
        public CalendarDay getItem(int position) {
            return CalendarDay.from(min.getDate().plusDays(Jdk8Methods.safeMultiply(position, DAYS_IN_TWO_WEEK)));
        }

        /**
         * Getting the first day of a week for a specific date based on a specific week day as first
         * day.
         */
        private CalendarDay getFirstDayOfWeek(@NonNull final CalendarDay day) {
            final LocalDate temp = day.getDate().with(WeekFields.of(firstDayOfWeek, 1).dayOfWeek(), 1L);
            return CalendarDay.from(temp);
        }
    }
}
