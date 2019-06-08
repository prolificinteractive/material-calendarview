package com.eagleteam.calendarview;

import android.support.v4.text.TextUtilsCompat;
import android.support.v4.view.ViewCompat;
import java.util.Locale;

class LocalUtils {

  private LocalUtils() { }

  static boolean isRTL() {
    return TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault())
        == ViewCompat.LAYOUT_DIRECTION_RTL;
  }
}
