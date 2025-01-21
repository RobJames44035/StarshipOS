/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.jittester.functions;

import jdk.test.lib.jittester.IRNode;
import jdk.test.lib.jittester.visitors.Visitor;

public class StaticConstructorDefinition extends IRNode {
    public StaticConstructorDefinition(IRNode body) {
        super(body.getResultType());
        this.owner = body.getOwner();
        addChild(body);
    }

    @Override
    public long complexity() {
        IRNode body = getChild(0);
        return body != null ? body.complexity() : 0;
    }

    @Override
    public<T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
