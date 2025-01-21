/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package jdk.test.lib.jittester;

import jdk.test.lib.jittester.visitors.Visitor;

public class Throw extends IRNode {
    public Throw(IRNode throwable) {
        super(throwable.getResultType());
        addChild(throwable);
    }

    @Override
    public long complexity() {
        return getThowable().complexity();
    }

    @Override
    public<T> T accept(Visitor<T> v) {
        return v.visit(this);
    }

    public IRNode getThowable() {
        return getChild(0);
    }
}
