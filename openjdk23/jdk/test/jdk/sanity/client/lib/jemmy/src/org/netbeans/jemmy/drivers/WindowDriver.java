/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.drivers;

import org.netbeans.jemmy.operators.ComponentOperator;

/**
 * Defines how to work with windows.
 */
public interface WindowDriver {

    /**
     * Activates a window.
     *
     * @param oper Window operator.
     */
    public void activate(ComponentOperator oper);

    /**
     * Requests a window to close.
     *
     * @param oper Window operator.
     */
    public void requestClose(ComponentOperator oper);

    /**
     * Closes a window by requesting it to close and then hiding it.
     *
     * @param oper Window operator.
     */
    public void requestCloseAndThenHide(ComponentOperator oper);

    /**
     * Closes a window by requesting it to close and then hiding it.
     *
     * @param oper Window operator.
     * @deprecated Use requestClose(ComponentOperator) instead.
     */
    @Deprecated
    public void close(ComponentOperator oper);

    /**
     * Change window location.
     *
     * @param oper Window operator.
     * @param x New x coordinate
     * @param y New y coordinate
     */
    public void move(ComponentOperator oper, int x, int y);

    /**
     * Change window size.
     *
     * @param oper Window operator.
     * @param width New window width.
     * @param height New window height.
     */
    public void resize(ComponentOperator oper, int width, int height);
}
