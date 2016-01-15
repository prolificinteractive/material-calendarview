package com.prolificinteractive.materialcalendarview;

import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class WeekPagerAdapter extends CalendarPagerAdapter<WeekView> {

    public WeekPagerAdapter(MaterialCalendarView mcv) {
        super(mcv);
    }

    @Override
    protected WeekView createView(int position) {
        return new WeekView(mcv, getItem(position), getFirstDayOfWeek());
    }

    @Override
    protected int indexOf(WeekView view) {
        CalendarDay week = view.getFirstViewDay();
        return getRangeIndex().indexOf(week);
    }

    @Override
    protected boolean isInstanceOfView(Object object) {
        return object instanceof WeekView;
    }

    @Override
    protected DateRangeIndex createRangeIndex(CalendarDay min, CalendarDay max) {
        return new Weekly(min, max, getFirstDayOfWeek());
    }

    public static class Weekly implements DateRangeIndex {

        private static final int DAYS_IN_WEEK = 7;
        private final CalendarDay min;
        private final int count;

        public Weekly(@NonNull CalendarDay min, @NonNull CalendarDay max, int firstDayOfWeek) {
            Calendar calendar = Calendar.getInstance();
            min.copyTo(calendar);
            calendar.set(Calendar.DAY_OF_WEEK, firstDayOfWeek);
            this.min = CalendarDay.from(calendar);
            this.count = weekNumberDifference(min, max);
        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public int indexOf(CalendarDay day) {
            return weekNumberDifference(min, day);
        }

        @Override
        public CalendarDay getItem(int position) {
            long minMillis = min.getDate().getTime();
            long millisOffset = TimeUnit.MILLISECONDS.convert(
                    position * DAYS_IN_WEEK,
                    TimeUnit.DAYS);
            long positionMillis = minMillis + millisOffset;
            return CalendarDay.from(new Date(positionMillis));
        }

        private int weekNumberDifference(@NonNull CalendarDay min, @NonNull CalendarDay max) {
            long millisDiff = max.getDate().getTime() - min.getDate().getTime();
            long dayDiff = TimeUnit.DAYS.convert(millisDiff, TimeUnit.MILLISECONDS);
            return (int) (dayDiff / DAYS_IN_WEEK);
        }
    }
}
