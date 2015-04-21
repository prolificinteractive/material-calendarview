package com.prolificinteractive.materialcalendarview.decorators;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;

import com.prolificinteractive.materialcalendarview.DayView;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;

/**
 * Created by castrelo on 11/04/15.
 */
public class HighlightDecorator implements DayViewDecorator{

    private int color = Color.parseColor("#8BC34A");

    public HighlightDecorator() {
    }

    public HighlightDecorator(int color) {
        this.color = color;
    }

    @Override
    public void decorate(DayView view,Context context) {
        view.setBackground(generateCircleDrawable(color));
    }

    private static Drawable generateCircleDrawable(final int color) {

        int r = 10;
        float[] outerR = new float[] {r, r, r, r, r, r, r, r};

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
