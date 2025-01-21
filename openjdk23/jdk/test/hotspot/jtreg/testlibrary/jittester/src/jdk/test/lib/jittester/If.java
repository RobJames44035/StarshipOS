/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package jdk.test.lib.jittester;

import jdk.test.lib.jittester.visitors.Visitor;

public class If extends IRNode {
    public enum IfPart {
        CONDITION,
        THEN,
        ELSE,
    }

    public If(IRNode condition, Block thenBlock, Block elseBlock, int level) {
        super(thenBlock.getResultType());
        this.level = level;
        resizeUpChildren(IfPart.values().length);
        setChild(IfPart.CONDITION.ordinal(), condition);
        setChild(IfPart.THEN.ordinal(), thenBlock);
        setChild(IfPart.ELSE.ordinal(), elseBlock);
    }

    @Override
    public long complexity() {
        IRNode condition = getChild(IfPart.CONDITION.ordinal());
        IRNode thenBlock = getChild(IfPart.THEN.ordinal());
        IRNode elseBlock = getChild(IfPart.ELSE.ordinal());
        return (condition != null ? condition.complexity() : 0)
            + Math.max(thenBlock != null ? thenBlock.complexity() : 0,
            elseBlock != null ? elseBlock.complexity() : 0);

    }

    @Override
    public long countDepth() {
        return Long.max(level, super.countDepth());
    }

    @Override
    public<T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
