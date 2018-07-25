package com.prolificinteractive.materialcalendarview;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.threeten.bp.Month.APRIL;
import static org.threeten.bp.Month.DECEMBER;
import static org.threeten.bp.Month.FEBRUARY;
import static org.threeten.bp.Month.JANUARY;
import static org.threeten.bp.Month.JULY;
import static org.threeten.bp.Month.JUNE;
import static org.threeten.bp.Month.MAY;
import static org.threeten.bp.Month.NOVEMBER;
import static org.threeten.bp.Month.OCTOBER;

public class MonthlyRangeIndexTest {

  @Test public void test400Years() {
    final CalendarDay january1816 = CalendarDay.from(1816, JANUARY.getValue(), 15);
    final CalendarDay january2216 = CalendarDay.from(2216, JANUARY.getValue(), 15);
    final CalendarDay april2018 = CalendarDay.from(2018, APRIL.getValue(), 1);
    final CalendarDay february1816 = CalendarDay.from(1816, FEBRUARY.getValue(), 1);

    final MonthPagerAdapter.Monthly monthly =
        new MonthPagerAdapter.Monthly(january1816, january2216);

    assertThat(monthly.getCount(), equalTo((2216 - 1816) * 12 + 1));

    assertThat(monthly.getItem((2018 - 1816) * 12 + 3), equalTo(april2018));
    assertThat(monthly.getItem(1), equalTo(february1816));

    assertThat(monthly.indexOf(january1816), equalTo(0));
    assertThat(monthly.indexOf(february1816), equalTo(1));
    assertThat(monthly.indexOf(april2018), equalTo((2018 - 1816) * 12 + 3));
    assertThat(monthly.indexOf(january2216), equalTo((2216 - 1816) * 12));
  }

  @Test public void test3Years() {
    final CalendarDay may2016 = CalendarDay.from(2016, MAY.getValue(), 6);
    final CalendarDay april2017 = CalendarDay.from(2017, MAY.getValue(), 1);
    final CalendarDay june2019 = CalendarDay.from(2019, JUNE.getValue(), 1);
    final CalendarDay july2019 = CalendarDay.from(2019, JULY.getValue(), 21);

    final MonthPagerAdapter.Monthly monthly = new MonthPagerAdapter.Monthly(may2016, july2019);

    assertThat(monthly.getCount(), equalTo((2019 - 2016) * 12 + 3));

    assertThat(monthly.getItem(12), equalTo(april2017));
    assertThat(monthly.getItem((2019 - 2016) * 12 + 1), equalTo(june2019));

    assertThat(monthly.indexOf(may2016), equalTo(0));
    assertThat(monthly.indexOf(april2017), equalTo(12));
    assertThat(monthly.indexOf(june2019), equalTo((2019 - 2016) * 12 + 1));
    assertThat(monthly.indexOf(july2019), equalTo((2019 - 2016) * 12 + 2));
  }

  @Test public void test2Years() {
    final CalendarDay may2016 = CalendarDay.from(2016, MAY.getValue(), 31);
    final CalendarDay may2018 = CalendarDay.from(2018, MAY.getValue(), 1);

    final MonthPagerAdapter.Monthly monthly = new MonthPagerAdapter.Monthly(may2016, may2018);

    assertThat(monthly.getCount(), equalTo(25));

    assertThat(monthly.getItem(25), equalTo(CalendarDay.from(2018, JUNE.getValue(), 1)));

    assertThat(monthly.indexOf(may2016), equalTo(0));
    assertThat(monthly.indexOf(may2018), equalTo((2018 - 2016) * 12));
  }

  @Test public void test1Year() {
    final CalendarDay january2018 = CalendarDay.from(2018, JANUARY.getValue(), 1);
    final CalendarDay december2018 = CalendarDay.from(2018, DECEMBER.getValue(), 31);
    final CalendarDay last = CalendarDay.from(2018, DECEMBER.getValue(), 1);

    final MonthPagerAdapter.Monthly monthly =
        new MonthPagerAdapter.Monthly(january2018, december2018);

    assertThat(monthly.getCount(), equalTo(12));

    assertThat(monthly.getItem(0), equalTo(january2018));
    assertThat(monthly.getItem(11), equalTo(last));

    assertThat(monthly.indexOf(january2018), equalTo(0));
    assertThat(monthly.indexOf(last), equalTo(11));
    assertThat(monthly.indexOf(december2018), equalTo(11));
  }

  @Test public void test3Months() {
    final CalendarDay may2018 = CalendarDay.from(2018, MAY.getValue(), 25);
    final CalendarDay june2018 = CalendarDay.from(2018, JUNE.getValue(), 1);
    final CalendarDay july2018 = CalendarDay.from(2018, JULY.getValue(), 5);

    final MonthPagerAdapter.Monthly monthly = new MonthPagerAdapter.Monthly(may2018, july2018);

    assertThat(monthly.getCount(), equalTo(3));

    assertThat(monthly.getItem(1), equalTo(june2018));

    assertThat(monthly.indexOf(may2018), equalTo(0));
    assertThat(monthly.indexOf(june2018), equalTo(1));
    assertThat(monthly.indexOf(july2018), equalTo(2));
  }

  @Test public void test2Months() {
    final CalendarDay october2018 = CalendarDay.from(2018, OCTOBER.getValue(), 31);
    final CalendarDay november2018 = CalendarDay.from(2018, NOVEMBER.getValue(), 1);

    final MonthPagerAdapter.Monthly monthly =
        new MonthPagerAdapter.Monthly(october2018, november2018);

    assertThat(monthly.getCount(), equalTo(2));

    assertThat(monthly.getItem(0), equalTo(CalendarDay.from(2018, OCTOBER.getValue(), 1)));
    assertThat(monthly.getItem(1), equalTo(CalendarDay.from(2018, NOVEMBER.getValue(), 1)));

    assertThat(monthly.indexOf(october2018), equalTo(0));
    assertThat(monthly.indexOf(november2018), equalTo(1));
  }

  @Test public void test1Month() {
    final CalendarDay startOctober2018 = CalendarDay.from(2018, OCTOBER.getValue(), 5);
    final CalendarDay endOctober2018 = CalendarDay.from(2018, OCTOBER.getValue(), 25);

    final MonthPagerAdapter.Monthly monthly =
        new MonthPagerAdapter.Monthly(endOctober2018, endOctober2018);

    assertThat(monthly.getCount(), equalTo(1));
    assertThat(monthly.getItem(0), equalTo(CalendarDay.from(2018, OCTOBER.getValue(), 1)));
    assertThat(monthly.indexOf(startOctober2018), equalTo(0));
    assertThat(monthly.indexOf(endOctober2018), equalTo(0));
  }
}