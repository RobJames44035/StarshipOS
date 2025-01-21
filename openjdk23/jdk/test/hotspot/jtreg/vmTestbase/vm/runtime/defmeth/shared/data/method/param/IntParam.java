/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package vm.runtime.defmeth.shared.data.method.param;

import vm.runtime.defmeth.shared.data.Visitor;

/**
 * Represents parameter constant value of type int.
 * Used to pass int constant as a parameter during method call.
 */
public class IntParam implements Param {
    int value;

    public IntParam(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    @Override
    public void visit(Visitor v) {
        v.visitParamInt(this);
    }
}
