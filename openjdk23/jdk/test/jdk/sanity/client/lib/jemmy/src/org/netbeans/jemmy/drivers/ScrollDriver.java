/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.drivers;

import org.netbeans.jemmy.drivers.scrolling.ScrollAdjuster;
import org.netbeans.jemmy.operators.ComponentOperator;

/**
 * Defines how to work with scrollable components such as
 * {@code javax.swing.JScrollBar}, {@code javax.swing.JScrollPane},
 * {@code javax.swing.JSlider}, ...
 *
 * @author Alexandre Iline (alexandre.iline@oracle.com)
 */
public interface ScrollDriver {

    /**
     * Changes value to a minimum.
     *
     * @param oper Scroller operator.
     * @param orientation {@code java.awt.Adjustable.HORIZONTAL} or
     * {@code java.awt.Adjustable.VERTICAL}
     */
    public void scrollToMinimum(ComponentOperator oper, int orientation);

    /**
     * Changes value to a maximum.
     *
     * @param oper Scroller operator.
     * @param orientation {@code java.awt.Adjustable.HORIZONTAL} or
     * {@code java.awt.Adjustable.VERTICAL}
     */
    public void scrollToMaximum(ComponentOperator oper, int orientation);

    /**
     * Changes value.
     *
     * @param oper Scroller operator.
     * @param adj Object defines scroll position.
     */
    public void scroll(ComponentOperator oper, ScrollAdjuster adj);
}
