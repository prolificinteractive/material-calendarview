package com.prolificinteractive.materialcalendarview;

import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Experimental
public class WeekPagerAdapter extends CalendarPagerAdapter<WeekView> {

    public WeekPagerAdapter(MaterialCalendarView mcv) {
        super(mcv);
    }

    @Override
    protected WeekView createView(int position) {
        return new WeekView(mcv, getItem(position), mcv.getFirstDayOfWeek());
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
        return new Weekly(min, max, mcv.getFirstDayOfWeek());
    }

    public static class Weekly implements DateRangeIndex {

        private static final int DAYS_IN_WEEK = 7;
        private final CalendarDay min;
        private final int count;

        public Weekly(@NonNull CalendarDay min, @NonNull CalendarDay max, int firstDayOfWeek) {
            this.min = getFirstDayOfWeek(min, firstDayOfWeek);
            this.count = weekNumberDifference(this.min, max) + 1;
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

            int dstOffsetMax = max.getCalendar().get(Calendar.DST_OFFSET);
            int dstOffsetMin = min.getCalendar().get(Calendar.DST_OFFSET);

            long dayDiff = TimeUnit.DAYS.convert(millisDiff + dstOffsetMax - dstOffsetMin, TimeUnit.MILLISECONDS);
            return (int) (dayDiff / DAYS_IN_WEEK);
        }

        /*
         * Necessary because of how Calendar handles getting the first day of week internally.
         */
        private CalendarDay getFirstDayOfWeek(@NonNull CalendarDay min, int wantedFirstDayOfWeek) {
            Calendar calendar = Calendar.getInstance();
            min.copyTo(calendar);
            while (calendar.get(Calendar.DAY_OF_WEEK) != wantedFirstDayOfWeek) {
                calendar.add(Calendar.DAY_OF_WEEK, -1);
            }
            return CalendarDay.from(calendar);
        }
    }
}
