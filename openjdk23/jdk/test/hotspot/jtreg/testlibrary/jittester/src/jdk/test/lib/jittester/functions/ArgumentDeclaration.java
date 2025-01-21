/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package jdk.test.lib.jittester.functions;

import jdk.test.lib.jittester.IRNode;
import jdk.test.lib.jittester.VariableInfo;
import jdk.test.lib.jittester.visitors.Visitor;

public class ArgumentDeclaration extends IRNode {
    public final VariableInfo variableInfo;

    public ArgumentDeclaration(VariableInfo variableInfo) {
        super(variableInfo.type);
        this.variableInfo = variableInfo;
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
