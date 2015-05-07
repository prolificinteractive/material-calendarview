package com.prolificinteractive.materialcalendarview;

import android.graphics.drawable.Drawable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Abstraction layer to help in decorating Day views
 */
public final class DayViewFacade {

    private boolean isDirty;

    private Drawable unselectedBackground = null;
    private Drawable background = null;
    private final LinkedList<Span> spans = new LinkedList<>();

    public DayViewFacade() {
        isDirty = false;
    }

    /**
     * Set the drawable to use in the 'normal' state of the Day view
     *
     * @param drawable Drawable to use, null for default
     */
    public void setBackgroundUnselected(Drawable drawable) {
        this.unselectedBackground = drawable;
        isDirty = true;
    }

    /**
     * Set the entire background drawable of the Day view.
     * This will take precedent over {@linkplain #setBackgroundUnselected(Drawable)}
     *
     * @param drawable the drawable for the Day view
     */
    public void setBackground(Drawable drawable) {
        background = drawable;
        isDirty = true;
    }

    /**
     * Add a span to the entire text of a day
     *
     * @param span text span instance
     */
    public void addSpan(Object span) {
        if(spans != null) {
            this.spans.add(new Span(span));
            isDirty = true;
        }
    }

    protected void reset() {
        unselectedBackground = null;
        background = null;
        spans.clear();
        isDirty = false;
    }

    /**
     * Apply things set this to other
     * @param other
     */
    protected void applyTo(DayViewFacade other) {
        if(background != null) {
            other.setBackground(background);
        }
        if(unselectedBackground != null) {
            other.setBackgroundUnselected(unselectedBackground);
        }
        other.spans.addAll(spans);
        other.isDirty |= this.isDirty;
    }

    public boolean isReset() {
        return !isDirty;
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
