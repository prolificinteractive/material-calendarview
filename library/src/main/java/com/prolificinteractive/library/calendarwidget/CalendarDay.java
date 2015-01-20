package com.prolificinteractive.library.calendarwidget;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * An imputable representation of a day on a calendar
 */
public final class CalendarDay implements Parcelable {

    private final int year;
    private final int month;
    private final int day;

    public CalendarDay() {
        this(Calendar.getInstance());
    }

    public CalendarDay(Calendar calendar) {
        this(
                calendar.get(YEAR),
                calendar.get(MONTH),
                calendar.get(DATE)
        );
    }

    public CalendarDay(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public CalendarDay(Date date) {
        this(CalendarWrapper.getInstance(date));
    }

    CalendarDay(CalendarWrapper selectedDate) {
        this(selectedDate.innerCalendar);
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public Date getDate() {
        return getCalendar().getTime();
    }

    public Calendar getCalendar() {
        Calendar calendar = Calendar.getInstance();
        copyTo(calendar);
        return calendar;
    }

    public void copyTo(Calendar calendar) {
        calendar.clear();
        calendar.set(year, month, day);
    }

    void copyTo(CalendarWrapper calendar) {
        calendar.clear();
        calendar.set(year, month, day);
    }

    public boolean isInRange(CalendarDay minDate, CalendarDay maxDate) {
        return !(minDate != null && minDate.isAfter(this)) &&
                !(maxDate != null && maxDate.isBefore(this));
    }

    public boolean isBefore(CalendarDay other) {
        return (year == other.year) ?
                ((month == other.month) ? (day < other.day) : (month < other.month)) :
                (year < other.year);
    }

    public boolean isAfter(CalendarDay other) {
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
