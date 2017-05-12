package android.support.v4.view;

import android.content.Context;
import android.util.AttributeSet;

import com.prolificinteractive.materialcalendarview.LocalUtils;

/**
 * {@linkplain #setChildrenDrawingOrderEnabledCompat(boolean)} does some reflection that isn't needed.
 * And was making view creation time rather large. So lets override it and make it better!
 */
public class BetterViewPager extends ViewPager {

    public BetterViewPager(Context context) {
        super(context);
        if (LocalUtils.isRTL())
            this.setRotationY(180);
    }

    public BetterViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setChildrenDrawingOrderEnabledCompat(boolean enable) {
        setChildrenDrawingOrderEnabled(enable);
    }
}
