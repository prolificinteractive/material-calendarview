package com.adamkoski.calendarwidget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.CheckedTextView;

/**
 * Display one day
 */
class DayView extends CheckedTextView {

    private CalendarDay date = new CalendarDay();

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
        setBackgroundDrawable(generateBackground());

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

    private Rect bounds = new Rect();

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        canvas.getClipBounds(bounds);
        bounds.inset(-8, -8);
        getBackground().setBounds(bounds);
        super.onDraw(canvas);
    }

    private static Drawable generateBackground() {
        StateListDrawable drawable = new StateListDrawable();
        drawable.setExitFadeDuration(200);
        drawable.addState(new int[] { android.R.attr.state_checked }, generateCircleDrawable(Color.MAGENTA));
        drawable.addState(new int[] { android.R.attr.state_pressed }, generateCircleDrawable(Color.RED));
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

    public CalendarDay getDate() {
        return date;
    }
}
