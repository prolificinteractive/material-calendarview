package com.prolificinteractive.materialcalendarview;

import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;

/**
 * Use math to calculate first days of months by postion from a minium date
 */
class DateRangeIndex {

    private final CalendarDay min;
    private final int count;

    private SparseArrayCompat<CalendarDay> dayCache = new SparseArrayCompat<>();

    public DateRangeIndex(@NonNull CalendarDay min, @NonNull CalendarDay max) {
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
