package com.prolificinteractive.library.calendarwidget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

/**
 * An {@linkplain android.widget.ImageView} to pragmatically set the color of arrows
 * using a {@linkplain android.graphics.ColorFilter}
 */
class DirectionButton extends ImageView {

    public DirectionButton(Context context) {
        super(context);
        init();
    }

    public DirectionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DirectionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DirectionButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setColor(Color.GRAY);

        TypedValue out = new TypedValue();
        Resources.Theme theme = getContext().getTheme();
        if(theme.resolveAttribute(android.R.attr.selectableItemBackground, out, true)) {
            setBackgroundResource(out.resourceId);
        }
    }

    public void setColor(int color) {
        setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setAlpha(enabled ? 1f : 0.1f);
    }
}
