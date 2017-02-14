package com.prolificinteractive.materialcalendarview.format;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Locale;

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
        assertThat(formatter.format(Calendar.SUNDAY).toString(), is("Sun"));
    }

    @Test
    public void testFormattedDayOfWeek_Monday() throws Exception {
        assertThat(formatter.format(Calendar.MONDAY).toString(), is("Mon"));
    }

    @Test
    public void testFormattedDayOfWeek_Tuesday() throws Exception {
        assertThat(formatter.format(Calendar.TUESDAY).toString(), is("Tue"));
    }

    @Test
    public void testFormattedDayOfWeek_Wednesday() throws Exception {
        assertThat(formatter.format(Calendar.WEDNESDAY).toString(), is("Wed"));
    }

    @Test
    public void testFormattedDayOfWeek_Thursday() throws Exception {
        assertThat(formatter.format(Calendar.THURSDAY).toString(), is("Thu"));
    }

    @Test
    public void testFormattedDayOfWeek_Friday() throws Exception {
        assertThat(formatter.format(Calendar.FRIDAY).toString(), is("Fri"));
    }

    @Test
    public void shouldReturnCorrectFormattedEnglishTextOfSaturday() throws Exception {
        assertThat(formatter.format(Calendar.SATURDAY).toString(), is("Sat"));
    }
}