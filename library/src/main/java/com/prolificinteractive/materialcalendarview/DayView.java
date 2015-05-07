package com.prolificinteractive.materialcalendarview;

import android.annotation.SuppressLint;
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
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckedTextView;

import java.util.List;

/**
 * Display one day of a {@linkplain MaterialCalendarView}
 */
@SuppressLint("ViewConstructor")
class DayView extends CheckedTextView {

    private CalendarDay date;
    private int selectionColor = Color.GRAY;

    private final int fadeTime;
    private Drawable customBackground = null;

    public DayView(Context context, CalendarDay day) {
        super(context);

        fadeTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        setSelectionColor(this.selectionColor);

        setGravity(Gravity.CENTER);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setTextAlignment(TEXT_ALIGNMENT_CENTER);
        }

        setDay(day);
    }

    public void setDay(CalendarDay date) {
        this.date = date;
        setText(getLabel());
    }

    public @NonNull String getLabel() {
        return String.valueOf(date.getDay());
    }

    public void setSelectionColor(int color) {
        this.selectionColor = color;
        regenerateBackground();
    }

    public void setCustomBackground(Drawable customBackground) {
        this.customBackground = customBackground;
        regenerateBackground();
    }

    public CalendarDay getDate() {
        return date;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    }

    protected void setupSelection(boolean showOtherDates, boolean inRange, boolean inMonth) {
        boolean enabled = inMonth && inRange;
        setEnabled(enabled);
        setVisibility(enabled || showOtherDates ? View.VISIBLE : View.INVISIBLE);
    }

    private void regenerateBackground() {
        setBackgroundDrawable(generateBackground(selectionColor, fadeTime, customBackground));
    }

    private static Drawable generateBackground(int color, int fadeTime, Drawable customBackground) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.setExitFadeDuration(fadeTime);
        drawable.addState(new int[]{android.R.attr.state_checked}, generateCircleDrawable(color));
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable.addState(new int[] { android.R.attr.state_pressed }, generateRippleDrawable(color));
        } else {
            drawable.addState(new int[] { android.R.attr.state_pressed }, generateCircleDrawable(color));
        }

        if(customBackground == null) {
            drawable.addState(new int[]{}, generateCircleDrawable(Color.TRANSPARENT));
        }
        else {
            drawable.addState(new int[]{}, customBackground);
        }

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

    void applyFacade(DayViewFacade facade) {
        if(facade.isReset()) {
            regenerateBackground();
            setText(getLabel());
            return;
        }

        Drawable background = facade.getBackground();
        Drawable unselectedBackground = facade.getUnselectedBackground();
        if (background != null) {
            setBackgroundDrawable(background);
        } else if (unselectedBackground != null) {
            setCustomBackground(unselectedBackground);
        }
        else {
            regenerateBackground();
        }

        List<DayViewFacade.Span> spans = facade.getSpans();
        if(!spans.isEmpty()) {
            String label = getLabel();
            SpannableString formattedLabel = new SpannableString(getLabel());
            for(DayViewFacade.Span span : spans) {
                formattedLabel.setSpan(span.span, 0, label.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            setText(formattedLabel);
        }
        else {
            setText(getLabel());
        }
    }
}
