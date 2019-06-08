package com.eagleteam.calendarview.sample.decorators;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import com.eagleteam.calendarview.CalendarDay;
import com.eagleteam.calendarview.DayViewDecorator;
import com.eagleteam.calendarview.DayViewFacade;
import com.eagleteam.calendarview.sample.R;

/**
 * Use a custom selector
 */
public class MySelectorDecorator implements DayViewDecorator {

  private final Drawable drawable;

  public MySelectorDecorator(Activity context) {
    drawable = context.getResources().getDrawable(R.drawable.my_selector);
  }

  @Override
  public boolean shouldDecorate(CalendarDay day) {
    return true;
  }

  @Override
  public void decorate(DayViewFacade view) {
    view.setSelectionDrawable(drawable);
  }
}
