package com.prolificinteractive.materialcalendarview;

import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;

/**
 * Pager adapter backing the calendar view
 */
class MonthPagerAdapter extends CalendarPagerAdapter<MonthView> {

    MonthPagerAdapter(MaterialCalendarView mcv) {
        super(mcv);
    }

    @Override
    protected MonthView createView(int position) {
        return new MonthView(mcv, getItem(position), mcv.getFirstDayOfWeek());
    }

    @Override
    protected int indexOf(MonthView view) {
        CalendarDay month = view.getMonth();
        return getRangeIndex().indexOf(month);
    }

    @Override
    protected boolean isInstanceOfView(Object object) {
        return object instanceof MonthView;
    }

    @Override
    protected DateRangeIndex createRangeIndex(CalendarDay min, CalendarDay max) {
        return new Monthly(min, max);
    }

    public static class Monthly implements DateRangeIndex {

        private final CalendarDay min;
        private final int count;

        private SparseArrayCompat<CalendarDay> dayCache = new SparseArrayCompat<>();

        public Monthly(@NonNull CalendarDay min, @NonNull CalendarDay max) {
            this.min = CalendarDay.from(min.getYear(), min.getMonth(), 1);
            max = CalendarDay.from(max.getYear(), max.getMonth(), 1);
            this.count = indexOf(max) + 1;
        }

        public int getCount() {
            return count;
        }

        public int indexOf(CalendarDay day) {
            int yDiff = day.getYear() - min.getYear();
            int mDiff = day.getMonth() - min.getMonth();

            return (yDiff * 12) + mDiff;
        }

        public CalendarDay getItem(int position) {

            CalendarDay re = dayCache.get(position);
            if (re != null) {
                return re;
            }

            int numY = position / 12;
            int numM = position % 12;

            int year = min.getYear() + numY;
            int month = min.getMonth() + numM;
            if (month >= 12) {
                year += 1;
                month -= 12;
            }

            re = CalendarDay.from(year, month, 1);
            dayCache.put(position, re);
            return re;
        }
    }
}
