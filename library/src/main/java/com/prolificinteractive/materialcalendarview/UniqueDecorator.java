package com.prolificinteractive.materialcalendarview;

import android.content.Context;

import com.prolificinteractive.materialcalendarview.spans.UniqueSpan;

import java.util.Date;
import java.util.HashMap;

public class UniqueDecorator implements DayViewDecorator {

    private final Context context;
    private int color;
    private HashMap<CalendarDay, String> dates;
    private int textSize, spacing;
    private boolean textUnderline;
    private CalendarDay date;

    public UniqueDecorator(Context context, int color, HashMap<CalendarDay, String> dates) {
        this.context = context;
        this.color = color;
        this.dates = new HashMap<>(dates);
        this.textSize = -1;
        this.spacing = -1;
    }

    public String getUniqueString(CalendarDay day) {
        return dates.get(day);
    }

    public boolean isTextUnderline() {
        return textUnderline;
    }

    public void setTextUnderline(boolean textUnderline) {
        this.textUnderline = textUnderline;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setTextSizeSP(int textSizeSP) {
        setTextSize(Utils.spToPx(context, textSizeSP));
    }

    public int getSpacing() {
        return spacing;
    }

    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }

    public void setSpacingDP(int spacing) {
        setSpacing(Utils.dpToPx(context, spacing));
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.containsKey(day) && (
                date == null || !day.equals(date)
                );
    }

    @Override
    public void decorate(DayViewFacade view) {
        if (textSize < 0)
            textSize = Utils.spToPx(context, 12);
        if (spacing < 0)
            spacing = Utils.dpToPx(context, 12);
        UniqueSpan uSpan = new UniqueSpan(color);
        uSpan.setTextUnderline(textUnderline);
        uSpan.setTextSize(textSize);
        uSpan.setSpacing(spacing);
        view.addSpan(uSpan);
    }

    public void setDate(Date date) {
        this.date = CalendarDay.from(date);
    }
}