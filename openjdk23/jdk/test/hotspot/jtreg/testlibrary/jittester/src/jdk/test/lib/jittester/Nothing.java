/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package jdk.test.lib.jittester;

import jdk.test.lib.jittester.visitors.Visitor;

public class Nothing extends IRNode {

    public Nothing() {
        super(TypeList.VOID);
    }

    @Override
    public long complexity() {
        return 0;
    }

    @Override
    public<T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
