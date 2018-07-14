package com.prolificinteractive.materialcalendarview.format;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;
import org.threeten.bp.DayOfWeek;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CalendarWeekDayFormatterTest {

    public CalendarWeekDayFormatter formatter;
    public Locale defaultLocaleOriginal;

    @Before
    public void setUp() throws Exception {
        defaultLocaleOriginal = Locale.getDefault();
        Locale.setDefault(Locale.ENGLISH);
        formatter = new CalendarWeekDayFormatter();
    }

    @After
    public void tearDown() throws Exception {
        Locale.setDefault(defaultLocaleOriginal);
    }

    @Test
    public void testFormattedDayOfWeek_Sunday() throws Exception {
        assertThat(formatter.format(DayOfWeek.SUNDAY).toString(), is("Sun"));
    }

    @Test
    public void testFormattedDayOfWeek_Monday() throws Exception {
        assertThat(formatter.format(DayOfWeek.MONDAY).toString(), is("Mon"));
    }

    @Test
    public void testFormattedDayOfWeek_Tuesday() throws Exception {
        assertThat(formatter.format(DayOfWeek.TUESDAY).toString(), is("Tue"));
    }

    @Test
    public void testFormattedDayOfWeek_Wednesday() throws Exception {
        assertThat(formatter.format(DayOfWeek.WEDNESDAY).toString(), is("Wed"));
    }

    @Test
    public void testFormattedDayOfWeek_Thursday() throws Exception {
        assertThat(formatter.format(DayOfWeek.THURSDAY).toString(), is("Thu"));
    }

    @Test
    public void testFormattedDayOfWeek_Friday() throws Exception {
        assertThat(formatter.format(DayOfWeek.FRIDAY).toString(), is("Fri"));
    }

    @Test
    public void shouldReturnCorrectFormattedEnglishTextOfSaturday() throws Exception {
        assertThat(formatter.format(DayOfWeek.SATURDAY).toString(), is("Sat"));
    }
}