package com.prolificinteractive.materialcalendarview;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.Gravity;
import com.prolificinteractive.materialcalendarview.format.WeekDayFormatter;
import java.time.DayOfWeek;

/**
 * Display a day of the week
 */
@SuppressLint("ViewConstructor") class WeekDayView extends AppCompatTextView {

  private WeekDayFormatter formatter = WeekDayFormatter.DEFAULT;
  private DayOfWeek dayOfWeek;

  public WeekDayView(final Context context, final DayOfWeek dayOfWeek) {
    super(context);

    setGravity(Gravity.CENTER);

    setTextAlignment(TEXT_ALIGNMENT_CENTER);
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
