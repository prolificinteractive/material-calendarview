package com.prolificinteractive.materialcalendarview;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.threeten.bp.DayOfWeek.FRIDAY;
import static org.threeten.bp.DayOfWeek.MONDAY;
import static org.threeten.bp.DayOfWeek.SATURDAY;
import static org.threeten.bp.DayOfWeek.SUNDAY;
import static org.threeten.bp.DayOfWeek.THURSDAY;
import static org.threeten.bp.DayOfWeek.TUESDAY;
import static org.threeten.bp.DayOfWeek.WEDNESDAY;
import static org.threeten.bp.Month.DECEMBER;
import static org.threeten.bp.Month.FEBRUARY;
import static org.threeten.bp.Month.JANUARY;
import static org.threeten.bp.Month.MARCH;

public class WeeklyRangeIndexTest {
  private static final int _2018 = 2018;

  @Test public void test1week() {
    final CalendarDay startJanuary2018 = CalendarDay.from(_2018, JANUARY.getValue(), 7);
    final CalendarDay endJanuary2018 = CalendarDay.from(_2018, JANUARY.getValue(), 13);

    final DateRangeIndex weekly =
        new WeekPagerAdapter.Weekly(startJanuary2018, endJanuary2018, SUNDAY);

    assertThat(weekly.getCount(), equalTo(1));

    assertThat(weekly.getItem(0), equalTo(CalendarDay.from(_2018, JANUARY.getValue(), 7)));

    assertThat(weekly.indexOf(startJanuary2018), equalTo(0));
    assertThat(weekly.indexOf(endJanuary2018), equalTo(0));
  }

  @Test public void test2weeksWith2Days() {
    final CalendarDay startJanuary2018 = CalendarDay.from(_2018, JANUARY.getValue(), 13);
    final CalendarDay endJanuary2018 = CalendarDay.from(_2018, JANUARY.getValue(), 14);

    final DateRangeIndex weekly =
        new WeekPagerAdapter.Weekly(startJanuary2018, endJanuary2018, SUNDAY);

    assertThat(weekly.getCount(), equalTo(2));

    assertThat(weekly.getItem(0), equalTo(CalendarDay.from(_2018, JANUARY.getValue(), 7)));

    assertThat(weekly.indexOf(startJanuary2018), equalTo(0));
    assertThat(weekly.indexOf(endJanuary2018), equalTo(1));
  }

  @Test public void test2weeksWith2DaysWithDifferentFirstDay() {
    final CalendarDay startJanuary2018 = CalendarDay.from(_2018, JANUARY.getValue(), 10);
    final CalendarDay endJanuary2018 = CalendarDay.from(_2018, JANUARY.getValue(), 11);

    final DateRangeIndex weekly =
        new WeekPagerAdapter.Weekly(startJanuary2018, endJanuary2018, THURSDAY);

    assertThat(weekly.getCount(), equalTo(2));

    assertThat(weekly.getItem(0), equalTo(CalendarDay.from(_2018, JANUARY.getValue(), 4)));

    assertThat(weekly.indexOf(startJanuary2018), equalTo(0));
    assertThat(weekly.indexOf(endJanuary2018), equalTo(1));
  }

  @Test public void test1weekStartingDifferentDays() {
    final CalendarDay startFebruary2018 = CalendarDay.from(_2018, FEBRUARY.getValue(), 7);
    final CalendarDay endFebruary2018 = CalendarDay.from(_2018, FEBRUARY.getValue(), 11);

    final DateRangeIndex monday =
        new WeekPagerAdapter.Weekly(startFebruary2018, endFebruary2018, MONDAY);
    final DateRangeIndex tuesday =
        new WeekPagerAdapter.Weekly(startFebruary2018, endFebruary2018, TUESDAY);
    final DateRangeIndex wednesday =
        new WeekPagerAdapter.Weekly(startFebruary2018, endFebruary2018, WEDNESDAY);
    final DateRangeIndex thursday =
        new WeekPagerAdapter.Weekly(startFebruary2018, endFebruary2018, THURSDAY);
    final DateRangeIndex friday =
        new WeekPagerAdapter.Weekly(startFebruary2018, endFebruary2018, FRIDAY);
    final DateRangeIndex saturday =
        new WeekPagerAdapter.Weekly(startFebruary2018, endFebruary2018, SATURDAY);
    final DateRangeIndex sunday =
        new WeekPagerAdapter.Weekly(startFebruary2018, endFebruary2018, SUNDAY);

    assertThat(monday.getItem(0), equalTo(CalendarDay.from(_2018, FEBRUARY.getValue(), 5)));
    assertThat(tuesday.getItem(0), equalTo(CalendarDay.from(_2018, FEBRUARY.getValue(), 6)));
    assertThat(wednesday.getItem(0), equalTo(CalendarDay.from(_2018, FEBRUARY.getValue(), 7)));
    assertThat(thursday.getItem(0), equalTo(CalendarDay.from(_2018, FEBRUARY.getValue(), 1)));
    assertThat(friday.getItem(0), equalTo(CalendarDay.from(_2018, FEBRUARY.getValue(), 2)));
    assertThat(saturday.getItem(0), equalTo(CalendarDay.from(_2018, FEBRUARY.getValue(), 3)));
    assertThat(sunday.getItem(0), equalTo(CalendarDay.from(_2018, FEBRUARY.getValue(), 4)));
  }

  @Test public void test2weeks() {
    final CalendarDay startJanuary2018 = CalendarDay.from(_2018, JANUARY.getValue(), 9);
    final CalendarDay endJanuary2018 = CalendarDay.from(_2018, JANUARY.getValue(), 18);

    final DateRangeIndex weekly =
        new WeekPagerAdapter.Weekly(startJanuary2018, endJanuary2018, SUNDAY);

    assertThat(weekly.getCount(), equalTo(2));

    assertThat(weekly.getItem(0), equalTo(CalendarDay.from(_2018, JANUARY.getValue(), 7)));
    assertThat(weekly.getItem(1), equalTo(CalendarDay.from(_2018, JANUARY.getValue(), 14)));

    assertThat(weekly.indexOf(startJanuary2018), equalTo(0));
    assertThat(weekly.indexOf(endJanuary2018), equalTo(1));
  }

  @Test public void test10weeks() {
    final CalendarDay january2018 = CalendarDay.from(_2018, JANUARY.getValue(), 9);
    final CalendarDay march2018 = CalendarDay.from(_2018, MARCH.getValue(), 12);

    final DateRangeIndex weekly = new WeekPagerAdapter.Weekly(january2018, march2018, SUNDAY);

    assertThat(weekly.getCount(), equalTo(10));

    assertThat(weekly.getItem(0), equalTo(CalendarDay.from(_2018, JANUARY.getValue(), 7)));
    assertThat(weekly.getItem(9), equalTo(CalendarDay.from(_2018, MARCH.getValue(), 11)));

    assertThat(weekly.indexOf(january2018), equalTo(0));
    assertThat(weekly.indexOf(march2018), equalTo(9));
  }

  @Test public void test52weeks() {
    final CalendarDay january2018 = CalendarDay.from(_2018, JANUARY.getValue(), 9);
    final CalendarDay january2019 = CalendarDay.from(2019, JANUARY.getValue(), 3);

    final DateRangeIndex weekly = new WeekPagerAdapter.Weekly(january2018, january2019, SUNDAY);

    assertThat(weekly.getCount(), equalTo(52));

    assertThat(weekly.getItem(0), equalTo(CalendarDay.from(_2018, JANUARY.getValue(), 7)));
    assertThat(weekly.getItem(51), equalTo(CalendarDay.from(2018, DECEMBER.getValue(), 30)));

    assertThat(weekly.indexOf(january2018), equalTo(0));
    assertThat(weekly.indexOf(january2019), equalTo(51));
  }

  @Test public void test1000weeks() {
    final CalendarDay january2018 = CalendarDay.from(_2018, JANUARY.getValue(), 11);
    final CalendarDay march2037 = CalendarDay.from(2037, MARCH.getValue(), 1);

    final DateRangeIndex weekly = new WeekPagerAdapter.Weekly(january2018, march2037, SUNDAY);

    assertThat(weekly.getCount(), equalTo(1000));

    assertThat(weekly.getItem(0), equalTo(CalendarDay.from(_2018, JANUARY.getValue(), 7)));
    assertThat(weekly.getItem(999), equalTo(CalendarDay.from(2037, MARCH.getValue(), 1)));

    assertThat(weekly.indexOf(january2018), equalTo(0));
    assertThat(weekly.indexOf(march2037), equalTo(999));
  }
}