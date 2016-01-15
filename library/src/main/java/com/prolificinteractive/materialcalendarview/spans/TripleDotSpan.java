package com.prolificinteractive.materialcalendarview.spans;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

/**
 * Span to draw 3 dots centered under a section of text
 */
public class TripleDotSpan implements LineBackgroundSpan {

    /**
     * Default radius used
     */
    public static final float DEFAULT_RADIUS = 3;

    private final float radius;
    private final int color1;
    private final int color2;
    private final int color3;

    private final Paint paint1 = new Paint();
    private final Paint paint2 = new Paint();
    private final Paint paint3 = new Paint();

    /**
     * Create a span to draw dots using default radius and colors
     *
     * @see #TripleDotSpan(float, int)
     * @see #DEFAULT_RADIUS
     */
    public TripleDotSpan() {
        this.radius = DEFAULT_RADIUS;
        this.color1 = 0;
        this.color2 = 0;
        this.color3 = 0;
        initializePaints();
    }

    /**
     * Create a span to draw dots using a specified color
     *
     * @param color color of the dots
     * @see #TripleDotSpan(float, int)
     * @see #DEFAULT_RADIUS
     */
    public TripleDotSpan(int color) {
        this.radius = DEFAULT_RADIUS;
        this.color1 = color;
        this.color2 = color;
        this.color3 = color;
        initializePaints();
    }

    /**
     * Create a span to draw dots using a specified radius
     *
     * @param radius radius for the dots
     * @see #TripleDotSpan(float, int)
     */
    public TripleDotSpan(float radius) {
        this.radius = radius;
        this.color1 = 0;
        this.color2 = 0;
        this.color3 = 0;
        initializePaints();
    }

    /**
     * Create a span to draw dots using a specified radius and color
     *
     * @param radius radius for the dots
     * @param color  color of the dots
     */
    public TripleDotSpan(float radius, int color) {
        this.radius = radius;
        this.color1 = color;
        this.color2 = color;
        this.color3 = color;
        initializePaints();
    }

    /**
     * Create a span to draw dots using a specified radius and colors
     *
     * @param radius radius for the dots
     * @param color1  color of the first dot
     * @param color2  color of the second dot
     * @param color3  color of the third dot
     */
    public TripleDotSpan(float radius, int color1, int color2, int color3) {
        this.radius = radius;
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
        initializePaints();
    }

    private void initializePaints() {
        this.paint1.setColor(color1);
        this.paint2.setColor(color2);
        this.paint3.setColor(color3);
    }

    @Override
    public void drawBackground(
            Canvas canvas, Paint paint,
            int left, int right, int top, int baseline, int bottom,
            CharSequence charSequence,
            int start, int end, int lineNum
    ) {
        int oldColor1 = paint1.getColor();
        int oldColor2 = paint2.getColor();
        int oldColor3 = paint3.getColor();
        if (color1 != 0) {
            paint1.setColor(color1);
        }
        if (color2 != 0) {
            paint2.setColor(color2);
        }
        if (color3 != 0) {
            paint3.setColor(color3);
        }

        int center = (left + right) / 2;

        int circleCenter1 = (int)(center - (4 * radius));
        int circleCenter2 = center;
        int circleCenter3 = (int)(center + (4 * radius));

        canvas.drawCircle(circleCenter1, bottom + radius, radius, paint1);
        canvas.drawCircle(circleCenter2, bottom + radius, radius, paint2);
        canvas.drawCircle(circleCenter3, bottom + radius, radius, paint3);

        paint1.setColor(oldColor1);
        paint2.setColor(oldColor2);
        paint3.setColor(oldColor3);
    }
}
