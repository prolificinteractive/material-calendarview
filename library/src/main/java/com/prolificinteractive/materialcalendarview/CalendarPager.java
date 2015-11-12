package com.prolificinteractive.materialcalendarview;

import android.content.Context;
import android.support.v4.view.BetterViewPager;
import android.view.MotionEvent;

/**
 * Custom ViewPager that allows swiping to be disabled.
 */
class CalendarPager extends BetterViewPager {

    private boolean pagingEnabled = true;

    public CalendarPager(Context context) {
        super(context);
    }

    /**
     * enable disable viewpager scroll
     *
     * @param pagingEnabled false to disable paging, true for paging (default)
     */
    public void setPagingEnabled(boolean pagingEnabled) {
        this.pagingEnabled = pagingEnabled;
    }

    /**
     * @return is this viewpager allowed to page
     */
    public boolean isPagingEnabled() {
        return pagingEnabled;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return pagingEnabled && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return pagingEnabled && super.onTouchEvent(ev);
    }
}
