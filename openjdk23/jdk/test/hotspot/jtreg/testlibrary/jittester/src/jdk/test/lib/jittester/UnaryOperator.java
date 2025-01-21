/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package jdk.test.lib.jittester;

import jdk.test.lib.jittester.visitors.Visitor;

public class UnaryOperator extends Operator {
    public UnaryOperator(OperatorKind opKind, IRNode expression) {
        super(opKind, expression.getResultType());
        addChild(expression);
    }

    @Override
    public long complexity() {
        IRNode expression = getChild(0);
        return expression != null ? expression.complexity() + 1 : 0;
    }

    @Override
    public<T> T accept(Visitor<T> v) {
        return v.visit(this);
    }

    public boolean isPrefix() {
        return opKind.isPrefix;
    }
}
