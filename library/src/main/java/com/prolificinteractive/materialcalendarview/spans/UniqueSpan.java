package com.prolificinteractive.materialcalendarview.spans;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

public class UniqueSpan implements LineBackgroundSpan {
    private String uniqueString;
    private int color;
    private int textSize;
    private boolean underline;
    private int spacing;

    public UniqueSpan(int color) {
        this.color = color;
        this.textSize = -1;
    }

    public UniqueSpan(UniqueSpan uniqueSpan) {
        this.color = uniqueSpan.getColor();
        this.textSize = uniqueSpan.getTextSize();
        this.underline = uniqueSpan.isTextUnderline();
        this.spacing = uniqueSpan.getSpacing();
        this.uniqueString = uniqueSpan.getUniqueString();
    }

    public int getTextSize() {
        return this.textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setUniqueString(String uniqueString) {
        this.uniqueString = uniqueString;
    }

    public String getUniqueString() {
        return this.uniqueString;
    }

    public boolean isTextUnderline() {
        return underline;
    }

    public void setTextUnderline(boolean underline) {
        this.underline = underline;
    }

    public int getColor() {
        return this.color;
    }

    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }

    public int getSpacing() {
        return spacing;
    }

    @Override
    public void drawBackground(Canvas canvas, Paint paint, int left, int right, int top,
                               int baseline, int bottom, CharSequence text,
                               int start, int end, int lnum) {

        if (uniqueString == null) return;

        int oldColor = paint.getColor();
        float oldTextSize = paint.getTextSize();

        paint.setColor(color);
        paint.setTextSize(textSize);
        paint.setUnderlineText(underline);

        float[] widths = new float[2];
        float width = 0f;
        paint.getTextWidths(uniqueString, widths);

        for (int i = 0; i < widths.length; i++) {
            width += widths[i];
        }

        float x = (left + right - width) / 2;
        float y = bottom + spacing;

        canvas.drawText(uniqueString, x, y, paint);

        paint.setColor(oldColor);
        paint.setTextSize(oldTextSize);
        paint.setUnderlineText(false);
    }
}
