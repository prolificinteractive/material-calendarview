package com.adamkoski.calendarwidget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.CheckedTextView;

import com.adamkoski.library.calendarwidget.R;

/**
 * Display one day
 */
class DayView extends CheckedTextView {

    private CalendarDay date = new CalendarDay();
    private int color = Color.GRAY;

    public DayView(Context context) {
        super(context);
        init();
    }

    public DayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DayView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setColor(this.color);

        setTextColor(getResources().getColorStateList(R.color.cw__indicator_text));

        int dp40 = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics()
        );
        setMinimumWidth(dp40);
        setMinimumHeight(dp40);

        setGravity(Gravity.CENTER);
        if(isInEditMode()) {
            setText("99");
        }
    }

    public void setDay(CalendarDay date) {
        this.date = date;
        setText(String.valueOf(date.getDay()));
    }

    public void setColor(int color) {
        this.color = color;
        setBackgroundDrawable(generateBackground(color));
    }

    private static Drawable generateBackground(int color) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.setExitFadeDuration(200);
        drawable.addState(new int[] { android.R.attr.state_checked }, generateCircleDrawable(color));
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable.addState(new int[] { android.R.attr.state_pressed }, generateRippleDrawable(color));
        }
        else {
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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static Drawable generateRippleDrawable(final int color) {
        ColorStateList list = ColorStateList.valueOf(color);
        Drawable mask = generateCircleDrawable(Color.WHITE);
        return new RippleDrawable(list, null, mask);
    }

    public CalendarDay getDate() {
        return date;
    }
}
