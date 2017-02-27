package com.prolificinteractive.materialcalendarview.sample;

import android.content.Context;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayView;

/**
 * Created by Martinus on 27-2-2017.
 */

public class CustomDayView extends DayView
{
    private static int[] StatePeriod = { R.attr.state_awesome };
    private boolean isAwesome;

    public boolean getAwesome() {
        return isAwesome;
    }

    public void setAwesome(boolean isAwesome) {
        this.isAwesome = isAwesome;
    }

    public CustomDayView(Context context, CalendarDay day)
    {
        super(context, day);

        //Do some awesome custom stuff here as well
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);

        if (isAwesome)
            mergeDrawableStates(drawableState, StatePeriod);

        return drawableState;
    }
}
