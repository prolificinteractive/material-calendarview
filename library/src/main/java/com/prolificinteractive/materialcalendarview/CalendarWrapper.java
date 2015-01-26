package com.prolificinteractive.materialcalendarview;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * Because Calendar can be annoying sometimes
 */
class CalendarWrapper implements Cloneable, Serializable, Comparable {

    final Calendar innerCalendar;

    private CalendarWrapper(Date date) {
        this();
        if(date != null) {
            innerCalendar.setTime(date);
        }
    }

    private CalendarWrapper() {
        super();
        innerCalendar = Calendar.getInstance();
    }

    public static CalendarWrapper getInstance() {
        CalendarWrapper calendar = new CalendarWrapper();
        copyDateTo(calendar, calendar);
        return calendar;
    }

    public static CalendarWrapper getInstance(Date date) {
        CalendarWrapper calendar = new CalendarWrapper(date);
        copyDateTo(calendar, calendar);
        return calendar;
    }

    public static void copyDateTo(CalendarWrapper from, CalendarWrapper to) {
        to.clear();
        to.set(
                from.getYear(),
                from.getMonth(),
                from.getDay()
        );
        to.setTimeZone(from.getTimeZone());
        to.getTimeInMillis();
    }

    public int getYear() {
        return get(YEAR);
    }

    public int getMonth() {
        return get(MONTH);
    }

    public int getDay() {
        return get(DATE);
    }

    public int getDayOfWeek() {
        return get(DAY_OF_WEEK);
    }

    public void setToFirstDay() {
        int year = getYear();
        int month = getMonth();
        clear();
        set(year, month, 1);
        getTimeInMillis();
    }

    public void set(int year, int month, int day) {
        innerCalendar.set(year, month, day);
    }

    public void clear() {
        innerCalendar.clear();
    }

    public void add(int field, int value) {
        innerCalendar.add(field, value);
    }

    protected void computeFields() {
        innerCalendar.get(DATE);
    }

    protected void computeTime() {
        innerCalendar.getTimeInMillis();
    }

    public int getGreatestMinimum(int field) {
        return innerCalendar.getGreatestMinimum(field);
    }

    public int getLeastMaximum(int field) {
        return innerCalendar.getLeastMaximum(field);
    }

    public int getMaximum(int field) {
        return innerCalendar.getMaximum(field);
    }

    public int getMinimum(int field) {
        return innerCalendar.getMinimum(field);
    }

    public void roll(int field, boolean increment) {
        innerCalendar.roll(field, increment);
    }

    public int get(int field) {
        return innerCalendar.get(field);
    }

    public Map<String, Integer> getDisplayNames(int field, int style, Locale locale) {
        return innerCalendar.getDisplayNames(field, style, locale);
    }

    public boolean after(Object calendar) {
        return innerCalendar.after(calendar);
    }

    public boolean before(Object calendar) {
        return innerCalendar.before(calendar);
    }

    public Object clone() throws CloneNotSupportedException {
        CalendarWrapper clone = (CalendarWrapper) super.clone();
        copyDateTo(this, clone);
        return clone;
    }

    public int getActualMaximum(int field) {
        return innerCalendar.getActualMaximum(field);
    }

    public int getActualMinimum(int field) {
        return innerCalendar.getActualMinimum(field);
    }

    public int getFirstDayOfWeek() {
        return innerCalendar.getFirstDayOfWeek();
    }

    public int getMinimalDaysInFirstWeek() {
        return innerCalendar.getMinimalDaysInFirstWeek();
    }

    public long getTimeInMillis() {
        return innerCalendar.getTimeInMillis();
    }

    public TimeZone getTimeZone() {
        return innerCalendar.getTimeZone();
    }

    public boolean isLenient() {
        return innerCalendar.isLenient();
    }

    public void roll(int field, int value) {
        innerCalendar.roll(field, value);
    }

    public void set(int field, int value) {
        innerCalendar.set(field, value);
    }

    public void setFirstDayOfWeek(int value) {
        innerCalendar.setFirstDayOfWeek(value);
    }

    public void setLenient(boolean value) {
        innerCalendar.setLenient(value);
    }

    public void setMinimalDaysInFirstWeek(int value) {
        innerCalendar.setMinimalDaysInFirstWeek(value);
    }

    public void setTimeInMillis(long milliseconds) {
        innerCalendar.setTimeInMillis(milliseconds);
    }

    public void setTimeZone(TimeZone timezone) {
        innerCalendar.setTimeZone(timezone);
    }

    public String toString() {
        return innerCalendar.toString();
    }

    public int compareTo(Object anotherCalendar) {
        if(anotherCalendar instanceof Calendar) {
            return innerCalendar.compareTo((Calendar) anotherCalendar);
        }
        if(anotherCalendar instanceof CalendarWrapper) {
            return innerCalendar.compareTo(((CalendarWrapper) anotherCalendar).innerCalendar);
        }
        return 0;
    }

    public String getDisplayName(int field, int style, Locale locale) {
        return innerCalendar.getDisplayName(field, style, locale);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof CalendarWrapper)) return false;

        CalendarWrapper that = (CalendarWrapper) o;

        if(!innerCalendar.equals(that.innerCalendar)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return innerCalendar.hashCode();
    }

    public Date getTime() {
        return innerCalendar.getTime();
    }
}
