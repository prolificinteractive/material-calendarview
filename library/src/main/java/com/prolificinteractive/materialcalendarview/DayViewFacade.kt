package com.prolificinteractive.materialcalendarview

import android.graphics.drawable.Drawable
import java.util.*

/**
 * Abstraction layer to help in decorating Day views
 */
class DayViewFacade {

    val isDecorated: Boolean
        get() = selectionDrawable != null || backgroundDrawable != null || spans.size > 0 || daysDisabled

    /** TODO
     * use a default value since setter does not allow null
     * */

    /**
     * Set a drawable to draw behind everything else
     *
     * Drawable to draw behind everything
     */
    var backgroundDrawable: Drawable? = null

    /** TODO
     * use a default value since setter does not allow null
     * */
    /**
     * Set a custom selection drawable
     * TODO: define states that can/should be used in StateListDrawables
     *
     */
    var selectionDrawable: Drawable? = null


    val spans = LinkedList<Span>()

    var daysDisabled = false


    /**
     * Add a span to the entire text of a day
     *
     * @param span text span instance
     */

    fun addSpan(span: Any?) {
        span?.let {
            spans.add(Span(span))
        }
    }

    fun reset() {
        backgroundDrawable = null
        selectionDrawable = null
        spans.clear()
        daysDisabled = false
    }

    /**
     * Apply things set this to other
     *
     * @param other facade to apply our data to
     */
    fun applyTo(other: DayViewFacade) {

        selectionDrawable?.let {
            other.selectionDrawable = it
        }

        backgroundDrawable?.let {
            other.selectionDrawable = it
        }

        other.spans.addAll(spans)
        other.daysDisabled = daysDisabled

    }

    /**
     * Are days from this facade disabled
     *
     * @return true if disabled, false if not re-enabled
     */
    fun areDaysDisabled(): Boolean {
        return daysDisabled
    }

    class Span(val span: Any)
}