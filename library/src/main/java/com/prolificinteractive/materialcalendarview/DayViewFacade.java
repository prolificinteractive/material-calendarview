package com.prolificinteractive.materialcalendarview;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Abstraction layer to help in decorating Day views
 */
public final class DayViewFacade {

    private boolean isReset;

    private Drawable unselectedBackground = null;
    private Drawable background = null;
    private final LinkedList<Span> spans = new LinkedList<>();

    public DayViewFacade() {
        isReset = true;
    }

    /**
     * Set the drawable to use in the 'normal' state of the Day view
     *
     * @param drawable Drawable to use, null for default
     */
    public void setBackgroundUnselected(Drawable drawable) {
        this.unselectedBackground = drawable;
        isReset = false;
    }

    /**
     * Set the entire background drawable of the Day view.
     * This will take precedent over {@linkplain #setBackgroundUnselected(Drawable)}
     *
     * @param drawable the drawable for the Day view
     */
    public void setBackground(Drawable drawable) {
        background = drawable;
        isReset = false;
    }

    /**
     * Add a span to the entire text of a day
     *
     * @param span text span instance
     */
    public void addSpan(Object span) {
        if(spans != null) {
            this.spans.add(new Span(span));
            isReset = false;
        }
    }

    protected void reset() {
        unselectedBackground = null;
        background = null;
        spans.clear();
        isReset = true;
    }

    public boolean isReset() {
        return isReset;
    }

    protected Drawable getBackground() {
        return background;
    }

    protected Drawable getUnselectedBackground() {
        return unselectedBackground;
    }

    protected List<Span> getSpans() {
        return Collections.unmodifiableList(spans);
    }

    protected static class Span {
        final Object span;

        /**
         * False == Start, True == End, Null == All
         */
        final Boolean position = null;

        public Span(Object span) {
            this.span  = span;
        }
    }
}
