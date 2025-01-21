/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package vm.runtime.defmeth.shared.data.method.body;

import vm.runtime.defmeth.shared.data.Visitor;

/**
 * Represents method body which returns integer constant.
 */
public class ReturnIntBody implements MethodBody {
    int value;

    public ReturnIntBody(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public void visit(Visitor v) {
        v.visitReturnIntBody(this);
    }
}
