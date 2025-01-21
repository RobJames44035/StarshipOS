/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.drivers;

import org.netbeans.jemmy.operators.ComponentOperator;

/**
 * Defines how to work with frames.
 */
public interface FrameDriver {

    /**
     * Iconifies a frame.
     *
     * @param oper Frame operator.
     */
    public void iconify(ComponentOperator oper);

    /**
     * Deiconifies a frame.
     *
     * @param oper Frame operator.
     */
    public void deiconify(ComponentOperator oper);

    /**
     * Maximizes a frame.
     *
     * @param oper Frame operator.
     */
    public void maximize(ComponentOperator oper);

    /**
     * Demaximizes a frame.
     *
     * @param oper Frame operator.
     */
    public void demaximize(ComponentOperator oper);
}
