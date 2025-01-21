/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy;

import java.awt.Component;

/**
 *
 * Exception can be thrown as a result of attempt to produce a mouse operation
 * for a component which is not visible.
 *
 * @author Alexandre Iline (alexandre.iline@oracle.com)
 */
public class ComponentIsNotVisibleException extends JemmyInputException {

    private static final long serialVersionUID = 42L;

    /**
     * Constructs a ComponentIsNotVisibleException object.
     *
     * @param comp a Component.
     */
    public ComponentIsNotVisibleException(Component comp) {
        super("Component is not visible", comp);
    }

}
