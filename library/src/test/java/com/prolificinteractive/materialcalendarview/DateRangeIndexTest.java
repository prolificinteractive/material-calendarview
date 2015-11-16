package com.prolificinteractive.materialcalendarview;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * Some tests to validate previous implementation is the same as the new indecizer
 */
public class DateRangeIndexTest {

    @Test
    public void testSingleMonth() {
        CalendarDay date = CalendarDay.from(2015, 6, 1);
        DateRangeIndex index = new MonthPagerAdapter.Monthly(date, date);

        assertEquals(1, index.getCount());
        assertEquals(date, index.getItem(0));
        assertEquals(0, index.indexOf(date));
    }

    @Test
    public void testBigRange() {
        Calendar minCal = Calendar.getInstance();
        minCal.set(1000, 0, 1);
        CalendarDay minDay = CalendarDay.from(minCal);

        Calendar maxCal = Calendar.getInstance();
        maxCal.set(3000, 0, 1);
        CalendarDay maxDay = CalendarDay.from(maxCal);

        DateRangeIndex index = new MonthPagerAdapter.Monthly(minDay, maxDay);

        assertEquals(2000 * 12 + 1, index.getCount());

        Calendar worker = CalendarUtils.getInstance();
        minDay.copyToMonthOnly(worker);
        CalendarDay workingMonth = CalendarDay.from(worker);
        int counter = 0;
        while (!maxDay.isBefore(workingMonth)) {
            CalendarDay day = CalendarDay.from(worker);

            assertEquals(day, index.getItem(counter));

            assertEquals(counter, index.indexOf(day));

            worker.add(Calendar.MONTH, 1);
            worker.set(Calendar.DAY_OF_MONTH, 1);
            workingMonth = CalendarDay.from(worker);
            counter++;
        }
    }

}