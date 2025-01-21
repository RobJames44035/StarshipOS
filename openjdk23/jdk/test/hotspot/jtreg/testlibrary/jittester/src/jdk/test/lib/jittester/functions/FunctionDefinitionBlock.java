/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package jdk.test.lib.jittester.functions;

import java.util.ArrayList;
import jdk.test.lib.jittester.IRNode;
import jdk.test.lib.jittester.TypeList;
import jdk.test.lib.jittester.types.TypeKlass;
import jdk.test.lib.jittester.visitors.Visitor;

public class FunctionDefinitionBlock extends IRNode {
    public FunctionDefinitionBlock(ArrayList<IRNode> content, int level, TypeKlass ownerClass) {
        super(TypeList.VOID);
        setOwner(ownerClass);
        addChildren(content);
        this.level = level;
    }

    @Override
    public long complexity() {
        int complexity = 0;
        for (IRNode child : getChildren()) {
            complexity += child.complexity();
        }
        return complexity;
    }

    @Override
    public<T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
