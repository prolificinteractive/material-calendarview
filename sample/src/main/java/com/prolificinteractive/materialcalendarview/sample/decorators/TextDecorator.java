package com.prolificinteractive.materialcalendarview.sample.decorators;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.Date;

/**
 * Decorate several days with a symbol
 */
public class TextDecorator implements DayViewDecorator {

    Collection<? extends Date> dates;
    CharSequence symbol;

    public TextDecorator(CharSequence symbol, Collection<? extends Date> dates) {
        this.symbol = symbol;
        this.dates = dates;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        for(Date date : dates)
            if(day.equals(CalendarDay.from(date))){
                return true;
            }
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        CharSequence text = view.getText();
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#CE93D8"));
        SpannableString spannableString = new SpannableString(symbol + " " + text);
        spannableString.setSpan(colorSpan, 0, 1, 0); // change dot color
        view.setText(spannableString);
    }
}
