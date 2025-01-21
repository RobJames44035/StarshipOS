/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.drivers;

import org.netbeans.jemmy.operators.ComponentOperator;

/**
 * Defines how to work with lists allowing items reordering.
 */
public interface OrderedListDriver extends MultiSelListDriver {

    /**
     * Changes item index.
     *
     * @param oper List operator.
     * @param itemIndex Current item index.
     * @param newIndex Ne witem index.
     */
    public void moveItem(ComponentOperator oper, int itemIndex, int newIndex);
}
