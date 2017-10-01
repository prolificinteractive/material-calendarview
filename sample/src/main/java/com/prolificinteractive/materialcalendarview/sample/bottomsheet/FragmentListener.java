package com.prolificinteractive.materialcalendarview.sample.bottomsheet;

import android.support.v4.app.Fragment;

import com.prolificinteractive.materialcalendarview.CalendarDay;

/**
 * @author : hafiq on 02/09/2017.
 */

public class FragmentListener {

    public interface DateChooseCallback {
        public void onDateResult(String id, CalendarDay day, String value);
    }
}
