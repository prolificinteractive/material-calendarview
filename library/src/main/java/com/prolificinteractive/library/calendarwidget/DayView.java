package com.prolificinteractive.library.calendarwidget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckedTextView;

/**
 * Display one day
 */
public class DayView extends CheckedTextView {

    private CalendarDay date = new CalendarDay();
    private int selectionColor = Color.GRAY;

    public DayView(Context context) {
        this(context, null);
    }

    public DayView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setSelectionColor(this.selectionColor);

        int size = getResources().getDimensionPixelSize(R.dimen.cw__default_day_size);
        setMinimumWidth(size);
        setMinimumHeight(size);

        setGravity(Gravity.CENTER);

        setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

        if(isInEditMode()) {
            setText("99");
        }
    }

    public void setDay(CalendarDay date) {
        this.date = date;
        setText(String.valueOf(date.getDay()));
    }

    public void setSelectionColor(int color) {
        this.selectionColor = color;
        setBackgroundDrawable(generateBackground(color));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static Drawable generateRippleDrawable(final int color) {
        ColorStateList list = ColorStateList.valueOf(color);
        Drawable mask = generateCircleDrawable(Color.WHITE);
        return new RippleDrawable(list, null, mask);
    }

    public CalendarDay getDate() {
        return date;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    }

    protected void setupSelection(boolean showOtherMonths, boolean inRange, boolean inMonth) {
        boolean enabled = inMonth && inRange;
        setEnabled(enabled);
        setVisibility(enabled || showOtherMonths ? View.VISIBLE : View.INVISIBLE);
    }

    private static Drawable generateBackground(int color) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.setExitFadeDuration(200);
        drawable.addState(new int[] { android.R.attr.state_checked }, generateCircleDrawable(color));
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable.addState(new int[] { android.R.attr.state_pressed }, generateRippleDrawable(color));
        } else {
            drawable.addState(new int[] { android.R.attr.state_pressed }, generateCircleDrawable(color));
        }
        drawable.addState(new int[] { }, generateCircleDrawable(Color.TRANSPARENT));
        return drawable;
    }

    private static Drawable generateCircleDrawable(final int color) {
        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
        drawable.setShaderFactory(new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                return new LinearGradient(0, 0, 0, 0, color, color, Shader.TileMode.REPEAT);
            }
        });
        return drawable;
    }
}
