package com.prolificinteractive.materialcalendarview.sample.decorators;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayView;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;

import java.util.Calendar;

/**
 * Created by castrelo on 11/04/15.
 */
public class HighlightWeekendsDecorator implements DayViewDecorator{


    @Override
    public boolean shouldDecorate(CalendarDay day) {
        int weekDay = day.getCalendar().get(Calendar.DAY_OF_WEEK);
        return weekDay == Calendar.SATURDAY || weekDay == Calendar.SUNDAY;
    }

    @Override
    public void decorate(DayView view) {
        view.setBackgroundDrawable(generateBackgroundDrawable());
    }

    private static Drawable generateBackgroundDrawable() {

        final int r = 0;
        final float[] outerR = new float[] {r, r, r, r, r, r, r, r};
        final int color = Color.parseColor("#228BC34A");

        RoundRectShape rr = new RoundRectShape(outerR, null, null);

        ShapeDrawable drawable = new ShapeDrawable(rr);
        drawable.setShaderFactory(new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                return new LinearGradient(0, 0, 0, 0, color, color, Shader.TileMode.REPEAT);
            }
        });
        return drawable;
    }
}
