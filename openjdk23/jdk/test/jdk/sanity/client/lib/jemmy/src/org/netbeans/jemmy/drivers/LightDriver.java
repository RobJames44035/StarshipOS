/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.drivers;

/**
 * Implements "light" model of driver because does not require to load classes
 * for all supported operator types.
 *
 * @see Driver
 *
 * @author Alexandre Iline (alexandre.iline@oracle.com)
 */
public interface LightDriver {

    /**
     * Returns array of operator classes which are supported by this driver.
     *
     * @return an array of supported operator classes' names.
     */
    public String[] getSupported();
}
