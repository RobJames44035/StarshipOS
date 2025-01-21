/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy;

import java.awt.Component;

/**
 *
 * This interface should be implemented to define the criteria used to search
 * for a component.
 *
 * @see org.netbeans.jemmy.ComponentSearcher
 * @see org.netbeans.jemmy.WindowWaiter
 *
 * @author Alexandre Iline (alexandre.iline@oracle.com)
 */
public interface ComponentChooser {

    /**
     * Check if the component argument meets the search criteria.
     *
     * @param comp Component to check.
     * @return {@code true} when the component conforms to the search
     * criteria; {@code false} otherwise.
     */
    public boolean checkComponent(Component comp);

    /**
     * Returns searched component description.
     *
     * @return a String representing the description value
     */
    public default String getDescription() {
        return toString();
    }
}
