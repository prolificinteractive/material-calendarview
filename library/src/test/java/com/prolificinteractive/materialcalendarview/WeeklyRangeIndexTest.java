package com.prolificinteractive.materialcalendarview;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WeeklyRangeIndexTest {

    private static final int FIRST_DAY_OF_WEEK = 1;
    private static final CalendarDay TEST_MIN = CalendarDay.from(1816, 0, 15);
    private static final CalendarDay TEST_MAX = CalendarDay.from(2216, 0, 15);

    @Test
    public void shouldReturnCorrectFirstDayForDisplayedWeek() throws Exception {
        DateRangeIndex systemUnderTest = new WeekPagerAdapter.Weekly(
                TEST_MIN,
                TEST_MAX,
                FIRST_DAY_OF_WEEK);

        int position = 10435;

        CalendarDay expected = CalendarDay.from(2016, 0, 10);
        CalendarDay computed = systemUnderTest.getItem(position);

        assertEquals(expected.getDate(), computed.getDate());
    }
}