/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package jdk.test.lib.jittester;

import jdk.test.lib.jittester.visitors.Visitor;

public class CastOperator extends Operator {

    public CastOperator(Type resultType, IRNode casted) {
        super(null, 13, resultType);
        addChild(casted);
    }

    @Override
    public long complexity() {
        return getChild(0).complexity() + 1;
    }

    @Override
    public<T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
