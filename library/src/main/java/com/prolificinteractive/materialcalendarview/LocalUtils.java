package com.prolificinteractive.materialcalendarview;

import androidx.core.text.TextUtilsCompat;
import androidx.core.view.ViewCompat;
import java.util.Locale;

class LocalUtils {

  private LocalUtils() { }

  static boolean isRTL() {
    return TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault())
        == ViewCompat.LAYOUT_DIRECTION_RTL;
  }
}
