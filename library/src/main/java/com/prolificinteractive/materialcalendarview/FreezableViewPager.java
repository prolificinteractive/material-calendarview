package com.prolificinteractive.materialcalendarview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Szabo Laszlo on 9/14/2015
 * Edited by
 */
public class FreezableViewPager extends ViewPager {

    public FreezableViewPager(Context context) {
        super(context);
    }

    public FreezableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getAdapter() instanceof MultiSelectionAdapter) {
            return !((MultiSelectionAdapter) getAdapter()).isInMultiSelectionMode() && super.onTouchEvent(event);
        } else return super.onTouchEvent(event);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (getAdapter() instanceof MultiSelectionAdapter) {
            return !((MultiSelectionAdapter) getAdapter()).isInMultiSelectionMode() && super.onInterceptTouchEvent(event);
        } else return super.onInterceptTouchEvent(event);
    }


}
