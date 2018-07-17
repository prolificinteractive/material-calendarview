package com.prolificinteractive.materialcalendarview;

import java.util.Calendar;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class WeeklyRangeIndexTest {
  private static final int _2018 = 2018;

  @Test public void test1week() {
    final CalendarDay startJanuary2018 = CalendarDay.from(_2018, Calendar.JANUARY, 9);
    final CalendarDay endJanuary2018 = CalendarDay.from(_2018, Calendar.JANUARY, 11);

    final DateRangeIndex weekly =
        new WeekPagerAdapter.Weekly(startJanuary2018, endJanuary2018, Calendar.SUNDAY);

    assertThat(weekly.getCount(), equalTo(1));

    assertThat(weekly.getItem(0), equalTo(CalendarDay.from(_2018, Calendar.JANUARY, 7)));

    assertThat(weekly.indexOf(startJanuary2018), equalTo(0));
    assertThat(weekly.indexOf(endJanuary2018), equalTo(0));
  }

  @Test public void test1weekStartingDifferentDays() {
    final CalendarDay startFebruary2018 = CalendarDay.from(_2018, Calendar.FEBRUARY, 7);
    final CalendarDay endFebruary2018 = CalendarDay.from(_2018, Calendar.FEBRUARY, 11);

    final DateRangeIndex monday =
        new WeekPagerAdapter.Weekly(startFebruary2018, endFebruary2018, Calendar.MONDAY);
    final DateRangeIndex tuesday =
        new WeekPagerAdapter.Weekly(startFebruary2018, endFebruary2018, Calendar.TUESDAY);
    final DateRangeIndex wednesday =
        new WeekPagerAdapter.Weekly(startFebruary2018, endFebruary2018, Calendar.WEDNESDAY);
    final DateRangeIndex thursday =
        new WeekPagerAdapter.Weekly(startFebruary2018, endFebruary2018, Calendar.THURSDAY);
    final DateRangeIndex friday =
        new WeekPagerAdapter.Weekly(startFebruary2018, endFebruary2018, Calendar.FRIDAY);
    final DateRangeIndex saturday =
        new WeekPagerAdapter.Weekly(startFebruary2018, endFebruary2018, Calendar.SATURDAY);
    final DateRangeIndex sunday =
        new WeekPagerAdapter.Weekly(startFebruary2018, endFebruary2018, Calendar.SUNDAY);

    assertThat(monday.getItem(0), equalTo(CalendarDay.from(_2018, Calendar.FEBRUARY, 5)));
    assertThat(tuesday.getItem(0), equalTo(CalendarDay.from(_2018, Calendar.FEBRUARY, 6)));
    assertThat(wednesday.getItem(0), equalTo(CalendarDay.from(_2018, Calendar.FEBRUARY, 7)));
    assertThat(thursday.getItem(0), equalTo(CalendarDay.from(_2018, Calendar.FEBRUARY, 1)));
    assertThat(friday.getItem(0), equalTo(CalendarDay.from(_2018, Calendar.FEBRUARY, 2)));
    assertThat(saturday.getItem(0), equalTo(CalendarDay.from(_2018, Calendar.FEBRUARY, 3)));
    assertThat(sunday.getItem(0), equalTo(CalendarDay.from(_2018, Calendar.FEBRUARY, 4)));
  }

  @Test public void test2weeks() {
    final CalendarDay startJanuary2018 = CalendarDay.from(_2018, Calendar.JANUARY, 9);
    final CalendarDay endJanuary2018 = CalendarDay.from(_2018, Calendar.JANUARY, 18);

    final DateRangeIndex weekly =
        new WeekPagerAdapter.Weekly(startJanuary2018, endJanuary2018, Calendar.SUNDAY);

    assertThat(weekly.getCount(), equalTo(2));

    assertThat(weekly.getItem(0), equalTo(CalendarDay.from(_2018, Calendar.JANUARY, 7)));
    assertThat(weekly.getItem(1), equalTo(CalendarDay.from(_2018, Calendar.JANUARY, 14)));

    assertThat(weekly.indexOf(startJanuary2018), equalTo(0));
    assertThat(weekly.indexOf(endJanuary2018), equalTo(1));
  }

  @Test public void test10weeks() {
    final CalendarDay january2018 = CalendarDay.from(_2018, Calendar.JANUARY, 9);
    final CalendarDay march2018 = CalendarDay.from(_2018, Calendar.MARCH, 12);

    final DateRangeIndex weekly =
        new WeekPagerAdapter.Weekly(january2018, march2018, Calendar.SUNDAY);

    assertThat(weekly.getCount(), equalTo(10));

    assertThat(weekly.getItem(0), equalTo(CalendarDay.from(_2018, Calendar.JANUARY, 7)));
    assertThat(weekly.getItem(9), equalTo(CalendarDay.from(_2018, Calendar.MARCH, 11)));

    assertThat(weekly.indexOf(january2018), equalTo(0));
    assertThat(weekly.indexOf(march2018), equalTo(9));
  }

  @Test public void test52weeks() {
    final CalendarDay january2018 = CalendarDay.from(_2018, Calendar.JANUARY, 9);
    final CalendarDay january2019 = CalendarDay.from(2019, Calendar.JANUARY, 3);

    final DateRangeIndex weekly =
        new WeekPagerAdapter.Weekly(january2018, january2019, Calendar.SUNDAY);

    assertThat(weekly.getCount(), equalTo(52));

    assertThat(weekly.getItem(0), equalTo(CalendarDay.from(_2018, Calendar.JANUARY, 7)));
    assertThat(weekly.getItem(51), equalTo(CalendarDay.from(2018, Calendar.DECEMBER, 30)));

    assertThat(weekly.indexOf(january2018), equalTo(0));
    assertThat(weekly.indexOf(january2019), equalTo(51));
  }

  @Test public void test1000weeks() {
    final CalendarDay january2018 = CalendarDay.from(_2018, Calendar.JANUARY, 11);
    final CalendarDay march2037 = CalendarDay.from(2037, Calendar.MARCH, 1);

    final DateRangeIndex weekly =
        new WeekPagerAdapter.Weekly(january2018, march2037, Calendar.SUNDAY);

    assertThat(weekly.getCount(), equalTo(1000));

    assertThat(weekly.getItem(0), equalTo(CalendarDay.from(_2018, Calendar.JANUARY, 7)));
    assertThat(weekly.getItem(999), equalTo(CalendarDay.from(2037, Calendar.MARCH, 1)));

    assertThat(weekly.indexOf(january2018), equalTo(0));
    assertThat(weekly.indexOf(march2037), equalTo(999));
  }
}