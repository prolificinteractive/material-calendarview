package com.adamkoski.calendarwidget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.adamkoski.library.calendarwidget.R;

/**
 *
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
        int dp40 = getResources().getDimensionPixelSize(R.dimen.cw__default_day_size);
        setLayoutParams(new ViewGroup.LayoutParams(dp40, dp40));

        TypedValue out = new TypedValue();
        Resources.Theme theme = getContext().getTheme();
        if(theme.resolveAttribute(android.R.attr.selectableItemBackground, out, true)) {
            setBackgroundResource(out.resourceId);
        }
    }

    public void setColor(int color) {
        setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }
}
