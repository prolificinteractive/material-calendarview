package com.prolificinteractive.materialcalendarview.spans;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.LineBackgroundSpan;
import android.text.style.StyleSpan;

import com.prolificinteractive.materialcalendarview.Utils;

public class OneDayUniqueSpan implements LineBackgroundSpan {
    private final Context context;
    private String uniqueString;
    private int colorDate, colorString;

    public OneDayUniqueSpan(Context context, int colorDate, int colorString, String string) {
        this.context = context;
        this.colorDate = colorDate;
        this.colorString = colorString;
        this.uniqueString = string;
    }

    public String getUniqueString() {
        return this.uniqueString;
    }

    public void setUniqueString(String uniqueString) {
        this.uniqueString = uniqueString;
    }

    @Override
    public void drawBackground(Canvas canvas, Paint paint, int left, int right, int top,
                               int baseline, int bottom, CharSequence text,
                               int start, int end, int lnum) {

        if (uniqueString == null) return;
        String[] strings = uniqueString.split("\n");

        int oldColor = paint.getColor();
        float oldTextSize = paint.getTextSize();

        paint.setColor(colorDate);
        paint.setTextSize(Utils.dpToPx(context, 12));

        float[] widths = new float[2];
        float width = 0f;

        paint.getTextWidths(strings[0], widths);
        for (int i = 0; i < widths.length; i++) {
            width += widths[i];
        }

        float x = (left + right - width) / 2;
        float y = strings.length > 1 ?
                Utils.dpToPx(context, 6) : Utils.dpToPx(context, 25 - 12);

        canvas.drawText(strings[0], x, y, paint);

        if (strings.length > 1) {
            paint.setColor(colorString);
            paint.setUnderlineText(true);
            paint.setTextSize(Utils.dpToPx(context, 12));

            float[] widths2 = new float[2];
            float width2 = 0f;

            paint.getTextWidths(strings[1], widths2);
            for (int i = 0; i < widths2.length; i++) {
                width2 += widths2[i];
            }
            float x2 = (left + right - width2) / 2;
            float y2 = Utils.dpToPx(context, 2 + 12 + 6);

            canvas.drawText(strings[1], x2, y2, paint);
        }

        paint.setColor(oldColor);
        paint.setTextSize(oldTextSize);
        paint.setUnderlineText(false);
    }
}
