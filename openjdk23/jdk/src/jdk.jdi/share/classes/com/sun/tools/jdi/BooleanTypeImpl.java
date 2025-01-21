/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

package com.sun.tools.jdi;

import com.sun.jdi.BooleanType;
import com.sun.jdi.InvalidTypeException;
import com.sun.jdi.PrimitiveValue;
import com.sun.jdi.VirtualMachine;

public class BooleanTypeImpl extends PrimitiveTypeImpl implements BooleanType {

    BooleanTypeImpl(VirtualMachine vm) {
        super(vm);
    }

    public String signature() {
        return String.valueOf((char)JDWP.Tag.BOOLEAN);
    }

    PrimitiveValue convert(PrimitiveValue value) throws InvalidTypeException {
        return vm.mirrorOf(((PrimitiveValueImpl)value).checkedBooleanValue());
    }
}
