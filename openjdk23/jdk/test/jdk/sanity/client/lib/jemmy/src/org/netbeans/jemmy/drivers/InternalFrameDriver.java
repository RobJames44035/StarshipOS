/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.drivers;

import java.awt.Component;

import org.netbeans.jemmy.operators.ComponentOperator;

/**
 * Defines the way to get a title pane.
 */
public interface InternalFrameDriver {

    /**
     * Returns the title pane component.
     *
     * @param oper operator for an internal frame.
     * @return a component - title pane.
     */
    public Component getTitlePane(ComponentOperator oper);
}
