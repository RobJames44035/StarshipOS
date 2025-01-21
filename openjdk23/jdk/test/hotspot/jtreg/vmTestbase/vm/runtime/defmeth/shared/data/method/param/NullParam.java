/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package vm.runtime.defmeth.shared.data.method.param;

import vm.runtime.defmeth.shared.data.Visitor;

/**
 * Represents null value as a parameter.
 * Used to pass null value as a parameter to method calls.
 */
public class NullParam implements Param {

    public NullParam() {}

    @Override
    public void visit(Visitor v) {
        v.visitParamNull();
    }
}
