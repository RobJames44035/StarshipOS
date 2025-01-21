/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package vm.runtime.defmeth.shared.data.method.param;

import vm.runtime.defmeth.shared.data.Visitor;

/**
 * Constant value for a method parameter of type j.l.String.
 * Used to pass a {@code String} as a method parameters during calls.
 */
public class StringParam implements Param {
    String value;

    public StringParam(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public void visit(Visitor v) {
        v.visitParamString(this);
    }

}
