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

    private boolean isDirty;

    private Drawable backgroundDrawable = null;
    private Drawable selectionDrawable = null;
    private final LinkedList<Span> spans = new LinkedList<>();

    public DayViewFacade() {
        isDirty = false;
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
        isDirty = true;
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
        isDirty = true;
    }

    /**
     * Add a span to the entire text of a day
     *
     * @param span text span instance
     */
    public void addSpan(@NonNull Object span) {
        if(spans != null) {
            this.spans.add(new Span(span));
            isDirty = true;
        }
    }

    protected void reset() {
        backgroundDrawable = null;
        selectionDrawable = null;
        spans.clear();
        isDirty = false;
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
        other.isDirty |= this.isDirty;
    }

    protected boolean isDirty() {
        return isDirty;
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

    protected static class Span {

        final Object span;

        public Span(Object span) {
            this.span  = span;
        }
    }
}
