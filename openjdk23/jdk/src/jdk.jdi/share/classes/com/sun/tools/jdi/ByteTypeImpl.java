/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

package com.sun.tools.jdi;

import com.sun.jdi.ByteType;
import com.sun.jdi.InvalidTypeException;
import com.sun.jdi.PrimitiveValue;
import com.sun.jdi.VirtualMachine;

public class ByteTypeImpl extends PrimitiveTypeImpl implements ByteType {

    ByteTypeImpl(VirtualMachine vm) {
        super(vm);
    }

    public String signature() {
        return String.valueOf((char)JDWP.Tag.BYTE);
    }

    PrimitiveValue convert(PrimitiveValue value) throws InvalidTypeException {
        return vm.mirrorOf(((PrimitiveValueImpl)value).checkedByteValue());
    }
}
