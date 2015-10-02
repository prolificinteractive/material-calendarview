package com.prolificinteractive.materialcalendarview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author <a href="http://www.lusfold.com" target="_blank">Lusfold</a>
 */
public class MonthPager extends ViewPager {
    private boolean scrollable = true;

    public MonthPager(Context context) {
        super(context);
    }

    public MonthPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * enable disable {@link #setCurrentItem(int)} to scroll
     *
     * @param x
     * @param y
     */
    @Override
    public void scrollTo(int x, int y) {
        if (scrollable) {
            super.scrollTo(x, y);
        }
    }

    /**
     * enable disable viewpager scroll
     *
     * @param scrollable
     */
    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }

    /**
     * @return is this viewpager scrollable
     */
    public boolean isScrollable() {
        return scrollable;
    }

    /**
     * enable disable touch to scroll
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return scrollable ? super.onInterceptTouchEvent(ev) : false;
    }
}