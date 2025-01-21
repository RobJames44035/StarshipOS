/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package jdk.test.lib.jittester.functions;

import java.util.ArrayList;
import jdk.test.lib.jittester.IRNode;
import jdk.test.lib.jittester.TypeList;
import jdk.test.lib.jittester.types.TypeKlass;
import jdk.test.lib.jittester.visitors.Visitor;

public class FunctionDeclarationBlock extends IRNode {
    public FunctionDeclarationBlock(TypeKlass ownerClass, ArrayList<IRNode> content, int level) {
        super(TypeList.VOID);
        setOwner(ownerClass);
        this.level = level;
        addChildren(content);
    }

    @Override
    public long complexity() {
        int complexity = 0;
        for (IRNode child : getChildren()) {
            complexity += child.complexity();
        }
        return complexity;
    }

    public int size() {
        return getChildren() != null ? getChildren().size() : 0;
    }

    @Override
    public<T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
