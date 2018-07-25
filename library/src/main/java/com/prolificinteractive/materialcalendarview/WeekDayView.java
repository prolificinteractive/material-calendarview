package com.prolificinteractive.materialcalendarview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import com.prolificinteractive.materialcalendarview.format.WeekDayFormatter;
import org.threeten.bp.DayOfWeek;

/**
 * Display a day of the week
 */
@SuppressLint("ViewConstructor") class WeekDayView extends AppCompatTextView {

  private WeekDayFormatter formatter = WeekDayFormatter.DEFAULT;
  private DayOfWeek dayOfWeek;

  public WeekDayView(final Context context, final DayOfWeek dayOfWeek) {
    super(context);

    setGravity(Gravity.CENTER);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
      setTextAlignment(TEXT_ALIGNMENT_CENTER);
    }

    setDayOfWeek(dayOfWeek);
  }

  public void setWeekDayFormatter(@Nullable final WeekDayFormatter formatter) {
    this.formatter = formatter == null ? WeekDayFormatter.DEFAULT : formatter;
    setDayOfWeek(dayOfWeek);
  }

  public void setDayOfWeek(final DayOfWeek dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
    setText(formatter.format(dayOfWeek));
  }
}
