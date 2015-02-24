package com.prolificinteractive.materialcalendarview;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * An imputable representation of a day on a calendar
 */
public final class CalendarDay implements Parcelable {

    private final int year;
    private final int month;
    private final int day;

    /**
     * Initialized to the current day
     */
    public CalendarDay() {
        this(CalendarUtils.getInstance());
    }

    /**
     * @param calendar source to pull date information from for this instance
     */
    public CalendarDay(Calendar calendar) {
        this(
                CalendarUtils.getYear(calendar),
                CalendarUtils.getMonth(calendar),
                CalendarUtils.getDay(calendar)
        );
    }

    /**
     * @param year new instance's year
     * @param month new instance's month as defined by {@linkplain java.util.Calendar}
     * @param day new instance's day of month
     */
    public CalendarDay(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * @param date source to pull date information from for this instance
     */
    public CalendarDay(Date date) {
        this(CalendarUtils.getInstance(date));
    }

    /**
     * @return the year for this day
     */
    public int getYear() {
        return year;
    }

    /**
     * @return the month of the year as defined by {@linkplain java.util.Calendar}
     */
    public int getMonth() {
        return month;
    }

    /**
     * @return the day of the month for this day
     */
    public int getDay() {
        return day;
    }

    /**
     * @return a date with this days information
     */
    public Date getDate() {
        return getCalendar().getTime();
    }

    /**
     * @return a new calendar instance with this day information
     */
    public Calendar getCalendar() {
        Calendar calendar = CalendarUtils.getInstance();
        copyTo(calendar);
        return calendar;
    }

    /**
     * @param calendar calendar to set date information to
     */
    public void copyTo(Calendar calendar) {
        calendar.clear();
        calendar.set(year, month, day);
    }

    /**
     * @param minDate the earliest day, may be null
     * @param maxDate the latest day, may be null
     * @return true if the between (inclusive) the min and max dates.
     */
    public boolean isInRange(CalendarDay minDate, CalendarDay maxDate) {
        return !(minDate != null && minDate.isAfter(this)) &&
                !(maxDate != null && maxDate.isBefore(this));
    }

    /**
     * @param other the other day to test
     * @return true if this is before other, false if equal or after
     */
    public boolean isBefore(CalendarDay other) {
        if(other == null) {
            throw new IllegalArgumentException("other cannot be null");
        }
        return (year == other.year) ?
                ((month == other.month) ? (day < other.day) : (month < other.month)) :
                (year < other.year);
    }

    /**
     * @param other the other day to test
     * @return true if this is after other, false if equal or before
     */
    public boolean isAfter(CalendarDay other) {
        if(other == null) {
            throw new IllegalArgumentException("other cannot be null");
        }
        return (year == other.year) ?
                ((month == other.month) ? (day > other.day) : (month > other.month)) :
                (year > other.year);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        CalendarDay that = (CalendarDay) o;

        if(day != that.day) return false;
        if(month != that.month) return false;
        if(year != that.year) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = year;
        result = 31 * result + month;
        result = 31 * result + day;
        return result;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "CalendarDay{%d-%d-%d}", year, month + 1, day);
    }

    /*
     * Parcelable Stuff
     */

    public CalendarDay(Parcel in) {
        this(in.readInt(), in.readInt(), in.readInt());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(year);
        dest.writeInt(month);
        dest.writeInt(day);
    }

    public static final Creator<CalendarDay> CREATOR = new Creator<CalendarDay>() {
        public CalendarDay createFromParcel(Parcel in) {
            return new CalendarDay(in);
        }

        public CalendarDay[] newArray(int size) {
            return new CalendarDay[size];
        }
    };
}
