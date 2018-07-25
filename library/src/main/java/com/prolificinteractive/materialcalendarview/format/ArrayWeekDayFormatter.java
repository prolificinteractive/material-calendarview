package com.prolificinteractive.materialcalendarview.format;

import org.threeten.bp.DayOfWeek;

/**
 * Use an array to supply week day labels
 */
public class ArrayWeekDayFormatter implements WeekDayFormatter {

  private final CharSequence[] weekDayLabels;

  /**
   * @param weekDayLabels an array of 7 labels, starting with Sunday
   */
  public ArrayWeekDayFormatter(final CharSequence[] weekDayLabels) {
    if (weekDayLabels == null) {
      throw new IllegalArgumentException("Cannot be null");
    }
    if (weekDayLabels.length != 7) {
      throw new IllegalArgumentException("Array must contain exactly 7 elements");
    }
    this.weekDayLabels = weekDayLabels;
  }

  /**
   * {@inheritDoc}
   */
  @Override public CharSequence format(final DayOfWeek dayOfWeek) {
    return weekDayLabels[dayOfWeek.getValue() - 1];
  }
}
