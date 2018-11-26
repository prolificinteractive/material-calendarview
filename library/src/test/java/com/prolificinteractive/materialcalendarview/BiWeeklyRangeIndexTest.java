package com.prolificinteractive.materialcalendarview;

import org.junit.Test;
import org.threeten.bp.DayOfWeek;
import org.threeten.bp.Month;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class BiWeeklyRangeIndexTest {
    private static final int _2018 = 2018;

    @Test
    public void test2week() {
        final CalendarDay startAugust2018 = CalendarDay.from(_2018, Month.AUGUST.getValue(), 14);
        final CalendarDay endAugust2018 = CalendarDay.from(_2018, Month.AUGUST.getValue(), 23);

        final DateRangeIndex biWeekly =
                new TwoWeekPagerAdapter.BiWeekly(startAugust2018, endAugust2018, DayOfWeek.SUNDAY);

        assertThat(biWeekly.getCount(), equalTo(1));

        assertThat(biWeekly.getItem(0), equalTo(CalendarDay.from(_2018, Month.AUGUST.getValue(), 12)));

        assertThat(biWeekly.indexOf(startAugust2018), equalTo(0));
        assertThat(biWeekly.indexOf(endAugust2018), equalTo(0));
    }

    @Test
    public void test4weeks() {
        final CalendarDay startAugust2018 = CalendarDay.from(_2018, Month.AUGUST.getValue(), 2);
        final CalendarDay endAugust2018 = CalendarDay.from(_2018, Month.AUGUST.getValue(), 23);

        final DateRangeIndex biWeekly =
                new TwoWeekPagerAdapter.BiWeekly(startAugust2018, endAugust2018, DayOfWeek.SUNDAY);

        assertThat(biWeekly.getCount(), equalTo(2));

        assertThat(biWeekly.getItem(0), equalTo(CalendarDay.from(_2018, Month.JULY.getValue(), 29)));
        assertThat(biWeekly.getItem(1), equalTo(CalendarDay.from(_2018, Month.AUGUST.getValue(), 12)));

        assertThat(biWeekly.indexOf(startAugust2018), equalTo(0));
        assertThat(biWeekly.indexOf(endAugust2018), equalTo(1));
    }
}