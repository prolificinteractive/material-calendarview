package com.prolificinteractive.materialcalendarview;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

public class CustomMultipleDotSpan implements LineBackgroundSpan {

    private final float radius;
    private int[] color = new int[0];
    private Activity context;


    public CustomMultipleDotSpan() {
        this.radius = 5;
        this.color[0] = 5;
    }


    public CustomMultipleDotSpan(int color) {
        this.radius = 5;
        this.color[0] = 0;
    }


    public CustomMultipleDotSpan(float radius) {
        this.radius = radius;
        this.color[0] = 0;
    }


    public CustomMultipleDotSpan(Activity context, float radius, int[] color) {
        this.radius = radius;
        this.color = color;
        this.context = context;
    }

    @Override
    public void drawBackground(
            Canvas canvas, Paint paint,
            int left, int right, int top, int baseline, int bottom,
            CharSequence charSequence,
            int start, int end, int lineNum
    ) {

        int total = color.length > 5 ? 5 : color.length;
        int leftMost = (total - 1) * -10;

        for (int i = 0; i < total; i++) {
            int oldColor = paint.getColor();
            if (color[i] != 0) {
                paint.setColor(color[i]);
            }
            int extraMarginDots = context.getResources().getDimensionPixelSize(R.dimen._5sdp) + 3;
            canvas.drawCircle((left + right) / 2 - leftMost, bottom + extraMarginDots, radius - 2, paint);
            paint.setColor(oldColor);
            leftMost = leftMost + 20;
        }
    }
}
