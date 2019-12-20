package com.prolificinteractive.materialcalendarview;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.threeten.bp.LocalDate;

/**
 * An imputable representation of a day on a calendar, based on {@link LocalDate}.
 */
public final class CalendarDay implements Parcelable {

  /**
   * Everything is based on this variable for {@link CalendarDay}.
   */
  @NonNull private final LocalDate date;

  /**
   * @param year new instance's year
   * @param month new instance's month as defined by {@linkplain java.util.Calendar}
   * @param day new instance's day of month
   */
  private CalendarDay(final int year, final int month, final int day) {
    date = LocalDate.of(year, month, day);
  }

  /**
   * @param date {@link LocalDate} instance
   */
  private CalendarDay(@NonNull final LocalDate date) {
    this.date = date;
  }

  /**
   * Get a new instance set to today
   *
   * @return CalendarDay set to today's date
   */
  @NonNull public static CalendarDay today() {
    return from(LocalDate.now());
  }

  /**
   * Get a new instance set to the specified day
   *
   * @param year new instance's year
   * @param month new instance's month as defined by {@linkplain java.util.Calendar}
   * @param day new instance's day of month
   * @return CalendarDay set to the specified date
   */
  @NonNull public static CalendarDay from(int year, int month, int day) {
    return new CalendarDay(year, month, day);
  }

  /**
   * Get a new instance set to the specified day
   *
   * @param date {@linkplain LocalDate} to pull date information from. Passing null will return null
   * @return CalendarDay set to the specified date
   */
  public static CalendarDay from(@Nullable LocalDate date) {
    if (date == null) {
      return null;
    }
    return new CalendarDay(date);
  }

  /**
   * Get the year
   *
   * @return the year for this day
   */
  public int getYear() {
    return date.getYear();
  }

  /**
   * Get the month, represented by values from {@linkplain LocalDate}
   *
   * @return the month of the year as defined by {@linkplain LocalDate}
   */
  public int getMonth() {
    return date.getMonthValue();
  }

  /**
   * Get the day
   *
   * @return the day of the month for this day
   */
  public int getDay() {
    return date.getDayOfMonth();
  }

  /**
   * Get this day as a {@linkplain LocalDate}
   *
   * @return a date with this days information
   */
  @NonNull public LocalDate getDate() {
    return date;
  }

  /**
   * Determine if this day is within a specified range
   *
   * @param minDate the earliest day, may be null
   * @param maxDate the latest day, may be null
   * @return true if the between (inclusive) the min and max dates.
   */
  public boolean isInRange(@Nullable CalendarDay minDate, @Nullable CalendarDay maxDate) {
    return !(minDate != null && minDate.isAfter(this)) &&
        !(maxDate != null && maxDate.isBefore(this));
  }

  /**
   * Determine if this day is before the given instance
   *
   * @param other the other day to test
   * @return true if this is before other, false if equal or after
   */
  public boolean isBefore(@NonNull final CalendarDay other) {
    return date.isBefore(other.getDate());
  }

  /**
   * Determine if this day is after the given instance
   *
   * @param other the other day to test
   * @return true if this is after other, false if equal or before
   */
  public boolean isAfter(@NonNull final CalendarDay other) {
    return date.isAfter(other.getDate());
  }

  @Override public boolean equals(Object o) {
    return o instanceof CalendarDay && date.equals(((CalendarDay) o).getDate());
  }

  @Override
  public int hashCode() {
    return hashCode(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
  }

  private static int hashCode(int year, int month, int day) {
    //Should produce hashes like "20150401"
    return (year * 10000) + (month * 100) + day;
  }

  @Override
  public String toString() {
    return "CalendarDay{" + date.getYear() + "-" + date.getMonthValue() + "-"
        + date.getDayOfMonth() + "}";
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
    dest.writeInt(date.getYear());
    dest.writeInt(date.getMonthValue());
    dest.writeInt(date.getDayOfMonth());
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
