/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */
package javax.swing.text.html;

import javax.swing.text.*;

/**
 * Processes the &lt;BR&gt; tag.  In other words, forces a line break.
 *
 * @author Sunita Mani
 */
class BRView extends InlineView {

    /**
     * Creates a new view that represents a &lt;BR&gt; element.
     *
     * @param elem the element to create a view for
     */
    public BRView(Element elem) {
        super(elem);
    }

    /**
     * Forces a line break.
     *
     * @return View.ForcedBreakWeight
     */
    public int getBreakWeight(int axis, float pos, float len) {
        if (axis == X_AXIS) {
            return ForcedBreakWeight;
        } else {
            return super.getBreakWeight(axis, pos, len);
        }
    }
}
