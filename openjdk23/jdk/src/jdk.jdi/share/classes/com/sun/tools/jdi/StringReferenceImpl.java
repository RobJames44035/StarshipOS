/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

package com.sun.tools.jdi;

import com.sun.jdi.StringReference;
import com.sun.jdi.Value;
import com.sun.jdi.VirtualMachine;

public class StringReferenceImpl extends ObjectReferenceImpl
    implements StringReference
{
    private String value;

    StringReferenceImpl(VirtualMachine aVm, long aRef) {
        super(aVm, aRef);
    }

    public String value() {
        if (value == null) {
            // Does not need synchronization, since worst-case
            // static info is fetched twice
            try {
                value = JDWP.StringReference.Value.
                    process(vm, this).stringValue;
            } catch (JDWPException exc) {
                throw exc.toJDIException();
            }
        }
        return value;
    }

    public String toString() {
        return "\"" + value() + "\"";
    }

    byte typeValueKey() {
        return JDWP.Tag.STRING;
    }
}
