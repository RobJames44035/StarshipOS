/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package com.sun.tools.jdi;

import java.util.ArrayList;
import java.util.List;

import com.sun.jdi.ClassNotLoadedException;
import com.sun.jdi.Type;
import com.sun.jdi.VirtualMachine;

/**
 * Represents methods which have changed when the class was redefined.
 */
public class ObsoleteMethodImpl extends NonConcreteMethodImpl {

    ObsoleteMethodImpl(VirtualMachine vm, ReferenceTypeImpl declaringType) {
        super(vm, declaringType, 0, "<obsolete>", "", null, 0);
    }

    public boolean isObsolete() {
        return true;
    }

    public String returnTypeName() {
        return "<unknown>";
    }

    public Type returnType() throws ClassNotLoadedException {
        throw new ClassNotLoadedException("type unknown");
    }

    public List<String> argumentTypeNames() {
        return new ArrayList<>();
    }

    public List<String> argumentSignatures() {
        return new ArrayList<>();
    }

    Type argumentType(int index) throws ClassNotLoadedException {
        throw new ClassNotLoadedException("type unknown");
    }

    public List<Type> argumentTypes() throws ClassNotLoadedException {
        return new ArrayList<>();
    }
}
