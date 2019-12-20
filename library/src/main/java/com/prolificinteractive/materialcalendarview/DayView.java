package com.prolificinteractive.materialcalendarview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckedTextView;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.Gravity;
import android.view.View;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView.ShowOtherDates;
import com.prolificinteractive.materialcalendarview.format.DayFormatter;
import java.util.List;

import static com.prolificinteractive.materialcalendarview.MaterialCalendarView.showDecoratedDisabled;
import static com.prolificinteractive.materialcalendarview.MaterialCalendarView.showOtherMonths;
import static com.prolificinteractive.materialcalendarview.MaterialCalendarView.showOutOfRange;

/**
 * Display one day of a {@linkplain MaterialCalendarView}
 */
@SuppressLint("ViewConstructor") class DayView extends AppCompatCheckedTextView {

  private CalendarDay date;
  private int selectionColor = Color.GRAY;

  private final int fadeTime;
  private Drawable customBackground = null;
  private Drawable selectionDrawable;
  private Drawable mCircleDrawable;
  private DayFormatter formatter = DayFormatter.DEFAULT;
  private DayFormatter contentDescriptionFormatter = formatter;

  private boolean isInRange = true;
  private boolean isInMonth = true;
  private boolean isDecoratedDisabled = false;
  @ShowOtherDates
  private int showOtherDates = MaterialCalendarView.SHOW_DEFAULTS;

  public DayView(Context context, CalendarDay day) {
    super(context);

    fadeTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

    setSelectionColor(this.selectionColor);

    setGravity(Gravity.CENTER);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
      setTextAlignment(TEXT_ALIGNMENT_CENTER);
    }

    setDay(day);
  }

  public void setDay(CalendarDay date) {
    this.date = date;
    setText(getLabel());
  }

  /**
   * Set the new label formatter and reformat the current label. This preserves current spans.
   *
   * @param formatter new label formatter
   */
  public void setDayFormatter(DayFormatter formatter) {
    this.contentDescriptionFormatter = contentDescriptionFormatter == this.formatter ?
                                       formatter : contentDescriptionFormatter;
    this.formatter = formatter == null ? DayFormatter.DEFAULT : formatter;
    CharSequence currentLabel = getText();
    Object[] spans = null;
    if (currentLabel instanceof Spanned) {
      spans = ((Spanned) currentLabel).getSpans(0, currentLabel.length(), Object.class);
    }
    SpannableString newLabel = new SpannableString(getLabel());
    if (spans != null) {
      for (Object span : spans) {
        newLabel.setSpan(span, 0, newLabel.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      }
    }
    setText(newLabel);
  }

  /**
   * Set the new content description formatter and reformat the current content description.
   *
   * @param formatter new content description formatter
   */
  public void setDayFormatterContentDescription(DayFormatter formatter) {
    this.contentDescriptionFormatter = formatter == null ? this.formatter : formatter;
    setContentDescription(getContentDescriptionLabel());
  }

  @NonNull
  public String getLabel() {
    return formatter.format(date);
  }

  @NonNull
  public String getContentDescriptionLabel() {
    return contentDescriptionFormatter == null ? formatter.format(date)
                                               : contentDescriptionFormatter.format(date);
  }

  public void setSelectionColor(int color) {
    this.selectionColor = color;
    regenerateBackground();
  }

  /**
   * @param drawable custom selection drawable
   */
  public void setSelectionDrawable(Drawable drawable) {
    if (drawable == null) {
      this.selectionDrawable = null;
    } else {
      this.selectionDrawable = drawable.getConstantState().newDrawable(getResources());
    }
    regenerateBackground();
  }

  /**
   * @param drawable background to draw behind everything else
   */
  public void setCustomBackground(Drawable drawable) {
    if (drawable == null) {
      this.customBackground = null;
    } else {
      this.customBackground = drawable.getConstantState().newDrawable(getResources());
    }
    invalidate();
  }

  public CalendarDay getDate() {
    return date;
  }

  private void setEnabled() {
    boolean enabled = isInMonth && isInRange && !isDecoratedDisabled;
    super.setEnabled(isInRange && !isDecoratedDisabled);

    boolean showOtherMonths = showOtherMonths(showOtherDates);
    boolean showOutOfRange = showOutOfRange(showOtherDates) || showOtherMonths;
    boolean showDecoratedDisabled = showDecoratedDisabled(showOtherDates);

    boolean shouldBeVisible = enabled;

    if (!isInMonth && showOtherMonths) {
      shouldBeVisible = true;
    }

    if (!isInRange && showOutOfRange) {
      shouldBeVisible |= isInMonth;
    }

    if (isDecoratedDisabled && showDecoratedDisabled) {
      shouldBeVisible |= isInMonth && isInRange;
    }

    if (!isInMonth && shouldBeVisible) {
      setTextColor(getTextColors().getColorForState(
          new int[] { -android.R.attr.state_enabled }, Color.GRAY));
    }
    setVisibility(shouldBeVisible ? View.VISIBLE : View.INVISIBLE);
  }

  protected void setupSelection(
      @ShowOtherDates int showOtherDates,
      boolean inRange,
      boolean inMonth) {
    this.showOtherDates = showOtherDates;
    this.isInMonth = inMonth;
    this.isInRange = inRange;
    setEnabled();
  }

  private final Rect tempRect = new Rect();
  private final Rect circleDrawableRect = new Rect();

  @Override
  protected void onDraw(@NonNull Canvas canvas) {
    if (customBackground != null) {
      customBackground.setBounds(tempRect);
      customBackground.setState(getDrawableState());
      customBackground.draw(canvas);
    }

    mCircleDrawable.setBounds(circleDrawableRect);

    super.onDraw(canvas);
  }

  private void regenerateBackground() {
    if (selectionDrawable != null) {
      setBackgroundDrawable(selectionDrawable);
    } else {
      mCircleDrawable = generateBackground(selectionColor, fadeTime, circleDrawableRect);
      setBackgroundDrawable(mCircleDrawable);
    }
  }

  private static Drawable generateBackground(int color, int fadeTime, Rect bounds) {
    StateListDrawable drawable = new StateListDrawable();
    drawable.setExitFadeDuration(fadeTime);
    drawable.addState(new int[] { android.R.attr.state_checked }, generateCircleDrawable(color));
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      drawable.addState(
          new int[] { android.R.attr.state_pressed },
          generateRippleDrawable(color, bounds)
      );
    } else {
      drawable.addState(new int[] { android.R.attr.state_pressed }, generateCircleDrawable(color));
    }

    drawable.addState(new int[] { }, generateCircleDrawable(Color.TRANSPARENT));

    return drawable;
  }

  private static Drawable generateCircleDrawable(final int color) {
    ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
    drawable.getPaint().setColor(color);
    return drawable;
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  private static Drawable generateRippleDrawable(final int color, Rect bounds) {
    ColorStateList list = ColorStateList.valueOf(color);
    Drawable mask = generateCircleDrawable(Color.WHITE);
    RippleDrawable rippleDrawable = new RippleDrawable(list, null, mask);
    //        API 21
    if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
      rippleDrawable.setBounds(bounds);
    }

    //        API 22. Technically harmless to leave on for API 21 and 23, but not worth risking for 23+
    if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1) {
      int center = (bounds.left + bounds.right) / 2;
      rippleDrawable.setHotspotBounds(center, bounds.top, center, bounds.bottom);
    }

    return rippleDrawable;
  }

  /**
   * @param facade apply the facade to us
   */
  void applyFacade(DayViewFacade facade) {
    this.isDecoratedDisabled = facade.areDaysDisabled();
    setEnabled();

    setCustomBackground(facade.getBackgroundDrawable());
    setSelectionDrawable(facade.getSelectionDrawable());

    // Facade has spans
    List<DayViewFacade.Span> spans = facade.getSpans();
    if (!spans.isEmpty()) {
      String label = getLabel();
      SpannableString formattedLabel = new SpannableString(getLabel());
      for (DayViewFacade.Span span : spans) {
        formattedLabel.setSpan(span.span, 0, label.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      }
      setText(formattedLabel);
    }
    // Reset in case it was customized previously
    else {
      setText(getLabel());
    }
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
    calculateBounds(right - left, bottom - top);
    regenerateBackground();
  }

  private void calculateBounds(int width, int height) {
    final int radius = Math.min(height, width);
    final int offset = Math.abs(height - width) / 2;

    // Lollipop platform bug. Circle drawable offset needs to be half of normal offset
    final int circleOffset =
        Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP ? offset / 2 : offset;

    if (width >= height) {
      tempRect.set(offset, 0, radius + offset, height);
      circleDrawableRect.set(circleOffset, 0, radius + circleOffset, height);
    } else {
      tempRect.set(0, offset, width, radius + offset);
      circleDrawableRect.set(0, circleOffset, width, radius + circleOffset);
    }
  }
}
