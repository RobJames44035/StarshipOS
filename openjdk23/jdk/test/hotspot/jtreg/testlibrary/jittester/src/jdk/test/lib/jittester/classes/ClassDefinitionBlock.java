/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package jdk.test.lib.jittester.classes;

import java.util.ArrayList;
import jdk.test.lib.jittester.IRNode;
import jdk.test.lib.jittester.TypeList;
import jdk.test.lib.jittester.visitors.Visitor;

public class ClassDefinitionBlock extends IRNode {
    public ClassDefinitionBlock(ArrayList<IRNode> content, int level) {
        super(TypeList.VOID);
        this.level = level;
        addChildren(content);
    }

    @Override
    public<T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
