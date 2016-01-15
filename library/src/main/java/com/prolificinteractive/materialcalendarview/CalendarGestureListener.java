package com.prolificinteractive.materialcalendarview;

import android.view.GestureDetector;
import android.view.MotionEvent;

class CalendarGestureListener extends GestureDetector.SimpleOnGestureListener {

    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

    private OnSwipeListener swipeListener;

    public CalendarGestureListener(OnSwipeListener listener) {
        this.swipeListener = listener;
    }

    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        boolean result = false;
        try {
            float diffY = event2.getY() - event1.getY();
            float diffX = event2.getX() - event1.getX();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        if(swipeListener != null) {
                            swipeListener.onSwipeRight();
                        }
                    } else {
                        if(swipeListener != null) {
                            swipeListener.onSwipeLeft();
                        }
                    }
                }
                result = true;
            }
            else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY > 0) {
                    if(swipeListener != null) {
                        swipeListener.onSwipeDown();
                    }
                } else {
                    if(swipeListener != null) {
                        swipeListener.onSwipeUp();
                    }
                }
            }
            result = true;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    public interface OnSwipeListener {
        void onSwipeUp();
        void onSwipeDown();
        void onSwipeLeft();
        void onSwipeRight();
    }
}