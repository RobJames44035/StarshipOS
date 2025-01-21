/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package jdk.test.lib.jittester.arrays;

import java.util.ArrayList;
import jdk.test.lib.jittester.IRNode;
import jdk.test.lib.jittester.types.TypeArray;
import jdk.test.lib.jittester.visitors.Visitor;

public class ArrayElement extends IRNode {
    public ArrayElement(IRNode array, ArrayList<IRNode> dimensionExpressions) {
        super(((TypeArray) array.getResultType()).type);
        addChild(array);
        addChildren(dimensionExpressions);
    }

    @Override
    public<T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
