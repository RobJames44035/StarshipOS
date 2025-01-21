/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.drivers;

import org.netbeans.jemmy.operators.ComponentOperator;

/**
 * Allows to declare supported operator classes.
 */
abstract public class SupportiveDriver implements Driver {

    private Class<?>[] supported;

    /**
     * Creates an instance.
     *
     * @param supported Array of operator classes which are supported by this
     * driver.
     */
    public SupportiveDriver(Class<?>[] supported) {
        this.supported = supported;
    }

    /**
     * Throws {@code UnsupportedOperatorException} exception if parameter's
     * class is not in list of supported classes.
     *
     * @param oper Operator whose class should be checked.
     * @throws UnsupportedOperatorException
     */
    public void checkSupported(ComponentOperator oper) {
        UnsupportedOperatorException.checkSupported(getClass(), supported, oper.getClass());
    }

    /**
     * Returns array of operator classes which are supported by this driver.
     */
    @Override
    public Class<?>[] getSupported() {
        return supported;
    }
}
