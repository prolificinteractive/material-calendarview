package com.prolificinteractive.materialcalendarview.decorators;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.DayView;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;

/**
 * Created by castrelo on 11/04/15.
 */
public class TextDecorator implements DayViewDecorator{


    CharSequence symbol = "●";

    public TextDecorator(CharSequence symbol) {
        this.symbol = symbol;
    }


    public TextDecorator(){}

    @Override
    public void decorate(DayView view, Context context) {

        CharSequence text = view.getText();
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#CE93D8"));
        SpannableString spannableString = new SpannableString(symbol + " " + text);
        spannableString.setSpan(colorSpan, 0, 1, 0); // change dot color
        view.setText(spannableString);
    }
}
