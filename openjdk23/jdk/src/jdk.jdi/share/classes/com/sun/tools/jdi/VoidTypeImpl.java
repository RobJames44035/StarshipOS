/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

package com.sun.tools.jdi;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.VoidType;

public class VoidTypeImpl extends TypeImpl implements VoidType {

    VoidTypeImpl(VirtualMachine vm) {
        super(vm);
    }

    public String signature() {
        return String.valueOf((char)JDWP.Tag.VOID);
    }

    public String toString() {
        return name();
    }
}
