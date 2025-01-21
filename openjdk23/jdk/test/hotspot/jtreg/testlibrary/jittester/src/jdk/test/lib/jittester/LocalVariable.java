/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package jdk.test.lib.jittester;

import jdk.test.lib.jittester.visitors.Visitor;

public class LocalVariable extends VariableBase {
    public LocalVariable(VariableInfo value) {
        super(value);
    }

    @Override
    public long complexity() {
        return 1;
    }

    @Override
    public<T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
