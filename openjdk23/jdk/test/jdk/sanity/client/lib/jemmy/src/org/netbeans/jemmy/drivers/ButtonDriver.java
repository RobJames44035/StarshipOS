/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.drivers;

import org.netbeans.jemmy.operators.ComponentOperator;

/**
 * Defines how to work with buttons.
 */
public interface ButtonDriver {

    /**
     * Presses a button.
     *
     * @param oper Button operator.
     */
    public void press(ComponentOperator oper);

    /**
     * Releases a button.
     *
     * @param oper Button operator.
     */
    public void release(ComponentOperator oper);

    /**
     * Pushes a button.
     *
     * @param oper Button operator.
     */
    public void push(ComponentOperator oper);
}
