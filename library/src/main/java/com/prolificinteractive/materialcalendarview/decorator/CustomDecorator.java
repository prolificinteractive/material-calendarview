package com.prolificinteractive.materialcalendarview.decorator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

public class CustomDecorator implements LineBackgroundSpan {
    private int[] color;
    private Context context;

    public final String TAG = CustomDecorator.class.getSimpleName();

    public CustomDecorator(int[] color, Context context) {
        this.color = color;
        this.context = context;
    }

    @Override
    public void drawBackground(Canvas canvas, Paint paint, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
        int total;
        int len = color.length;
        if (len > 4) {
            total = 4;
        } else {
            total = color.length;
        }
        int dpi = context.getResources().getDisplayMetrics().densityDpi;
        int radius;
        float leftMost;
        float leftMostConst;
        int dental;
        float centerX = (left + right)/2f;
        float yLine = 0;

        paint.setAntiAlias(true);
        if (dpi <= 420) {
            if (dpi >= 320) {
                radius = 5;
            } else {
                radius = 4;
            }
            leftMost = (total/2f) * -(20) + 10;
            leftMostConst = leftMost;
            dental = 20;
        } else {
            if (dpi < 560) {
                radius = 6;
            } else {
                radius = 7;
            }

            leftMost = (total/2f) * -(25) + 12;
            leftMostConst = leftMost;
            dental = 25;
        }
        for (int i = 0;i<len;i++) {
            if(i % total == 0 && i > 0){
                yLine += radius*2.2;
                leftMost = leftMostConst;
            }
            int oldColor = paint.getColor();
            if (color[i] != 0) {
                paint.setColor(color[i]);
            }
            canvas.drawCircle(centerX + leftMost, bottom + yLine, radius, paint);
            paint.setColor(oldColor);
            leftMost = leftMost + dental;
        }
    }
}
