/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package jdk.test.lib.jittester;

import java.util.ArrayList;

import jdk.test.lib.jittester.visitors.Visitor;

public class VariableDeclarationBlock extends IRNode {
    public VariableDeclarationBlock(ArrayList<? extends Declaration> content, int level) {
        super(TypeList.VOID);
        addChildren(content);
        this.level = level;
    }

    @Override
    public long complexity() {
        return getChildren()
                .stream()
                .mapToLong(IRNode::complexity)
                .sum();
    }

    @Override
    public<T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
