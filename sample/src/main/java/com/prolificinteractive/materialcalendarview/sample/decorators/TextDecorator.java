package com.prolificinteractive.materialcalendarview.sample.decorators;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayView;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;

import java.util.Collection;
import java.util.Date;

/**
 * Created by castrelo on 11/04/15.
 */
public class TextDecorator implements DayViewDecorator{


    Collection<Date> dates;
    CharSequence symbol;


    public TextDecorator(CharSequence symbol, Collection<Date> dates) {
        this.symbol = symbol;
        this.dates = dates;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        for(Date date : dates)
            if(day.equals(new CalendarDay(date))){
                return true;
            }
        return false;
    }

    @Override
    public void decorate(DayView view) {
        CharSequence text = view.getText();
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#CE93D8"));
        SpannableString spannableString = new SpannableString(symbol + " " + text);
        spannableString.setSpan(colorSpan, 0, 1, 0); // change dot color
        view.setText(spannableString);
    }
}
