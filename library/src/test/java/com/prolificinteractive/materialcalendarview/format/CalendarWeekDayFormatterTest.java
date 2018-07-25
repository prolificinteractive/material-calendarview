package com.prolificinteractive.materialcalendarview.format;

import java.util.Locale;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.DayOfWeek;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CalendarWeekDayFormatterTest {

  public CalendarWeekDayFormatter formatter;
  public Locale defaultLocaleOriginal;

  @Before public void setUp() {
    defaultLocaleOriginal = Locale.getDefault();
    Locale.setDefault(Locale.ENGLISH);
    formatter = new CalendarWeekDayFormatter();
  }

  @After public void tearDown() {
    Locale.setDefault(defaultLocaleOriginal);
  }

  @Test public void testFormattedDayOfWeek_Sunday() {
    assertThat(formatter.format(DayOfWeek.SUNDAY).toString(), is("Sun"));
  }

  @Test public void testFormattedDayOfWeek_Monday() {
    assertThat(formatter.format(DayOfWeek.MONDAY).toString(), is("Mon"));
  }

  @Test public void testFormattedDayOfWeek_Tuesday() {
    assertThat(formatter.format(DayOfWeek.TUESDAY).toString(), is("Tue"));
  }

  @Test public void testFormattedDayOfWeek_Wednesday() {
    assertThat(formatter.format(DayOfWeek.WEDNESDAY).toString(), is("Wed"));
  }

  @Test public void testFormattedDayOfWeek_Thursday() {
    assertThat(formatter.format(DayOfWeek.THURSDAY).toString(), is("Thu"));
  }

  @Test public void testFormattedDayOfWeek_Friday() {
    assertThat(formatter.format(DayOfWeek.FRIDAY).toString(), is("Fri"));
  }

  @Test public void shouldReturnCorrectFormattedEnglishTextOfSaturday() {
    assertThat(formatter.format(DayOfWeek.SATURDAY).toString(), is("Sat"));
  }
}