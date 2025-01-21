/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.drivers;

/**
 * Implements "heavy" model of driver because requires to load classes for all
 * supported operator types.
 *
 * @see LightDriver
 *
 * @author Alexandre Iline (alexandre.iline@oracle.com)
 */
public interface Driver {

    /**
     * Returns an array of operator classes which are supported by this driver.
     *
     * @return an array of supported operators' classes.
     */
    public Class<?>[] getSupported();
}
