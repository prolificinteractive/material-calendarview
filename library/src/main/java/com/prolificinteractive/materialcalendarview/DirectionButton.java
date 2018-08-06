package com.prolificinteractive.materialcalendarview;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.AppCompatImageView;
import android.util.TypedValue;

/**
 * An {@linkplain android.widget.ImageView} to pragmatically set the color of arrows
 * using a {@linkplain android.graphics.ColorFilter}
 */
class DirectionButton extends AppCompatImageView {

  public DirectionButton(Context context) {
    super(context);

    setBackgroundResource(getThemeSelectableBackgroundId(context));
  }

  @Override
  public void setEnabled(boolean enabled) {
    super.setEnabled(enabled);
    setAlpha(enabled ? 1f : 0.1f);
  }

  private static int getThemeSelectableBackgroundId(Context context) {
    //Get selectableItemBackgroundBorderless defined for AppCompat
    int colorAttr = context.getResources().getIdentifier(
        "selectableItemBackgroundBorderless", "attr", context.getPackageName());

    if (colorAttr == 0) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        colorAttr = android.R.attr.selectableItemBackgroundBorderless;
      } else {
        colorAttr = android.R.attr.selectableItemBackground;
      }
    }

    TypedValue outValue = new TypedValue();
    context.getTheme().resolveAttribute(colorAttr, outValue, true);
    return outValue.resourceId;
  }
}
