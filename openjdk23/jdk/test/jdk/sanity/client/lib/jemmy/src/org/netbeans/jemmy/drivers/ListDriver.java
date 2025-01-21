/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.drivers;

import org.netbeans.jemmy.operators.ComponentOperator;

/**
 * Defines how to work with lists.
 */
public interface ListDriver {

    /**
     * Selects an item.
     *
     * @param oper List operator.
     * @param index Item index.
     */
    public void selectItem(ComponentOperator oper, int index);
}
