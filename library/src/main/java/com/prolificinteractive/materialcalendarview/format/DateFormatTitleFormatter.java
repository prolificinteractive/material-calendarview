package com.prolificinteractive.materialcalendarview.format;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import org.threeten.bp.format.DateTimeFormatter;

/**
 * Format using a {@linkplain java.text.DateFormat} instance.
 */
public class DateFormatTitleFormatter implements TitleFormatter {

  private final DateTimeFormatter dateFormat;

  /**
   * Format using {@link TitleFormatter#DEFAULT_FORMAT} for formatting.
   */
  public DateFormatTitleFormatter() {
    this(DateTimeFormatter.ofPattern(DEFAULT_FORMAT));
  }

  /**
   * Format using a specified {@linkplain DateTimeFormatter}
   *
   * @param format the format to use
   */
  public DateFormatTitleFormatter(final DateTimeFormatter format) {
    this.dateFormat = format;
  }

  /**
   * {@inheritDoc}
   */
  @Override public CharSequence format(final CalendarDay day) {
    return dateFormat.format(day.getDate());
  }
}
