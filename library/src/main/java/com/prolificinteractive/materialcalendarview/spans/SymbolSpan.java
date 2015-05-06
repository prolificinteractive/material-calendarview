package com.prolificinteractive.materialcalendarview.spans;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;
import android.text.Layout;
import android.text.ParcelableSpan;
import android.text.Spanned;
import android.text.style.LeadingMarginSpan;

public class SymbolSpan implements LeadingMarginSpan, ParcelableSpan {

    private final String mSymbol;
    private final int mGapWidth;
    private final boolean mWantColor;
    private final int mColor;

    public static final int STANDARD_GAP_WIDTH = 1;

    public SymbolSpan(String symbol) {
        mSymbol = symbol;
        mGapWidth = STANDARD_GAP_WIDTH;
        mWantColor = false;
        mColor = 0;
    }

    public SymbolSpan(String symbol, int color) {
        mSymbol = symbol;
        mGapWidth = STANDARD_GAP_WIDTH;
        mWantColor = true;
        mColor = color;
    }

    public SymbolSpan(String symbol, int color, int gap) {
        mSymbol = symbol;
        mGapWidth = gap;
        mWantColor = true;
        mColor = color;
    }

    public SymbolSpan(Parcel src) {
        mSymbol = src.readString();
        mGapWidth = src.readInt();
        mWantColor = src.readInt() != 0;
        mColor = src.readInt();
    }

    @Override
    public int getSpanTypeId() {
        return 1000;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSymbol);
        dest.writeInt(mGapWidth);
        dest.writeInt(mWantColor ? 1 : 0);
        dest.writeInt(mColor);
    }

    @Override
    public int getLeadingMargin(boolean first) {
        return mGapWidth;
    }

    /**
     * Renders the leading margin.  This is called before the margin has been
     * adjusted by the value returned by {@link #getLeadingMargin(boolean)}.
     *
     * @param canvas the canvas
     * @param paint the paint. The this should be left unchanged on exit.
     * @param x the current position of the margin
     * @param dir the base direction of the paragraph; if negative, the margin
     * is to the right of the text, otherwise it is to the left.
     * @param top the top of the line
     * @param baseline the baseline of the line
     * @param bottom the bottom of the line
     * @param text the text
     * @param start the start of the line
     * @param end the end of the line
     * @param first true if this is the first line of its paragraph
     * @param layout the layout containing this line
     */
    @Override
    public void drawLeadingMargin(
            Canvas canvas,
            Paint paint,
            int x,
            int dir,
            int top,
            int baseline,
            int bottom,
            CharSequence text,
            int start,
            int end,
            boolean first,
            Layout layout
    ) {
        if (((Spanned) text).getSpanStart(this) == start) {
            Paint.Style style = paint.getStyle();
            int oldColor = 0;

            if (mWantColor) {
                oldColor = paint.getColor();
                paint.setColor(mColor);
            }

            canvas.drawText(mSymbol, start, baseline, paint);

            if (mWantColor) {
                paint.setColor(oldColor);
            }

            paint.setStyle(style);
        }
    }
}
