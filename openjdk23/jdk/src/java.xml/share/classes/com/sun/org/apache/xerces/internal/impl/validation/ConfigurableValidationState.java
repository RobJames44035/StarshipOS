/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package com.sun.org.apache.xerces.internal.impl.validation;

import java.util.Iterator;

/**
 * <p>An extension of ValidationState which can be configured to turn
 * off checking for ID/IDREF errors and unparsed entity errors.</p>
 *
 * @xerces.internal
 *
 * @author Peter McCracken, IBM
 * @LastModified: Oct 2017
 */
public final class ConfigurableValidationState extends ValidationState {

    /**
     * Whether to check for ID/IDREF errors
     */
    private boolean fIdIdrefChecking;

    /**
     * Whether to check for unparsed entity errors
     */
    private boolean fUnparsedEntityChecking;

    /**
     * Creates a new ConfigurableValidationState.
     * By default, error checking for both ID/IDREFs
     * and unparsed entities are turned on.
     */
    public ConfigurableValidationState() {
        super();
        fIdIdrefChecking = true;
        fUnparsedEntityChecking = true;
    }

    /**
     * Turns checking for ID/IDREF errors on and off.
     * @param setting true to turn on error checking,
     *                 false to turn off error checking
     */
    public void setIdIdrefChecking(boolean setting) {
        fIdIdrefChecking = setting;
    }

    /**
     * Turns checking for unparsed entity errors on and off.
     * @param setting true to turn on error checking,
     *                 false to turn off error checking
     */
    public void setUnparsedEntityChecking(boolean setting) {
        fUnparsedEntityChecking = setting;
    }

    /**
     * Checks if all IDREFs have a corresponding ID.
     * @return null, if ID/IDREF checking is turned off
     *         otherwise, returns the value of the super implementation
     */
    public Iterator<String> checkIDRefID() {
        return (fIdIdrefChecking) ? super.checkIDRefID() : null;
    }

    /**
     * Checks if an ID has already been declared.
     * @return false, if ID/IDREF checking is turned off
     *         otherwise, returns the value of the super implementation
     */
    public boolean isIdDeclared(String name) {
        return (fIdIdrefChecking) ? super.isIdDeclared(name) : false;
    }

    /**
     * Checks if an entity is declared.
     * @return true, if unparsed entity checking is turned off
     *         otherwise, returns the value of the super implementation
     */
    public boolean isEntityDeclared(String name) {
        return (fUnparsedEntityChecking) ? super.isEntityDeclared(name) : true;
    }

    /**
     * Checks if an entity is unparsed.
     * @return true, if unparsed entity checking is turned off
     *         otherwise, returns the value of the super implementation
     */
    public boolean isEntityUnparsed(String name) {
        return (fUnparsedEntityChecking) ? super.isEntityUnparsed(name) : true;
    }

    /**
     * Adds the ID, if ID/IDREF checking is enabled.
     * @param name the ID to add
     */
    public void addId(String name) {
        if (fIdIdrefChecking) {
            super.addId(name);
        }
    }

    /**
     * Adds the IDREF, if ID/IDREF checking is enabled.
     * @param name the IDREF to add
     */
    public void addIdRef(String name) {
        if (fIdIdrefChecking) {
            super.addIdRef(name);
        }
    }
}
