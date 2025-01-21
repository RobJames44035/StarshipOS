/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package jdk.test.lib.jittester.loops;

import jdk.test.lib.jittester.IRNode;
import jdk.test.lib.jittester.Initialization;
import jdk.test.lib.jittester.VariableInfo;
import jdk.test.lib.jittester.visitors.Visitor;

public class CounterInitializer extends Initialization {
    public CounterInitializer(VariableInfo varInfo, IRNode expression) {
        super(varInfo, expression);
    }

    @Override
    public<T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
