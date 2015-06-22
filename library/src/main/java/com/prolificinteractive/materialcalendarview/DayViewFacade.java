package com.prolificinteractive.materialcalendarview;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Abstraction layer to help in decorating Day views
 */
public final class DayViewFacade {

    private boolean isDecorated;

    private Drawable backgroundDrawable = null;
    private Drawable selectionDrawable = null;
    private final LinkedList<Span> spans = new LinkedList<>();
    private boolean daysDisabled = false;

    public DayViewFacade() {
        isDecorated = false;
    }

    /**
     * Set a drawable to draw behind everything else
     *
     * @param drawable Drawable to draw behind everything
     */
    public void setBackgroundDrawable(@NonNull Drawable drawable) {
        if(drawable == null) {
            throw new IllegalArgumentException("Cannot be null");
        }
        this.backgroundDrawable = drawable;
        isDecorated = true;
    }

    /**
     * Set a custom selection drawable
     *
     * TODO: define states that can/should be used in StateListDrawables
     *
     * @param drawable the drawable for selection
     */
    public void setSelectionDrawable(@NonNull Drawable drawable) {
        if(drawable == null) {
            throw new IllegalArgumentException("Cannot be null");
        }
        selectionDrawable = drawable;
        isDecorated = true;
    }

    /**
     * Add a span to the entire text of a day
     *
     * @param span text span instance
     */
    public void addSpan(@NonNull Object span) {
        if(spans != null) {
            this.spans.add(new Span(span));
            isDecorated = true;
        }
    }

    /**
     * <p>Set days to be in a disabled state, or re-enabled.</p>
     *
     * <p>Note, passing true here will <b>not</b> override minimum and maximum dates, if set.
     * This will only re-enable disabled dates.</p>
     */
    public void setDaysDisabled(boolean daysDisabled) {
        this.daysDisabled = daysDisabled;
        this.isDecorated = true;
    }

    protected void reset() {
        backgroundDrawable = null;
        selectionDrawable = null;
        spans.clear();
        isDecorated = false;
        daysDisabled = false;
    }

    /**
     * Apply things set this to other
     * @param other facade to apply our data to
     */
    protected void applyTo(DayViewFacade other) {
        if(selectionDrawable != null) {
            other.setSelectionDrawable(selectionDrawable);
        }
        if(backgroundDrawable != null) {
            other.setBackgroundDrawable(backgroundDrawable);
        }
        other.spans.addAll(spans);
        other.isDecorated |= this.isDecorated;
        other.daysDisabled = daysDisabled;
    }

    protected boolean isDecorated() {
        return isDecorated;
    }

    protected Drawable getSelectionDrawable() {
        return selectionDrawable;
    }

    protected Drawable getBackgroundDrawable() {
        return backgroundDrawable;
    }

    protected List<Span> getSpans() {
        return Collections.unmodifiableList(spans);
    }

    public boolean areDaysDisabled() {
        return daysDisabled;
    }

    protected static class Span {

        final Object span;

        public Span(Object span) {
            this.span  = span;
        }
    }
}
