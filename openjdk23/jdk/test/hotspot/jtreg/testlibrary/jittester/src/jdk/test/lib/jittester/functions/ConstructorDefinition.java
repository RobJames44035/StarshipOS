/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package jdk.test.lib.jittester.functions;

import java.util.ArrayList;
import jdk.test.lib.jittester.IRNode;
import jdk.test.lib.jittester.visitors.Visitor;

public class ConstructorDefinition extends IRNode {
    private final FunctionInfo functionInfo;

    public ConstructorDefinition(FunctionInfo functionInfo,
            ArrayList<ArgumentDeclaration> argumentsDeclaration, IRNode body) {
        super(functionInfo.type);
        this.functionInfo = functionInfo;
        this.owner = functionInfo.owner;
        addChild(body);
        addChildren(argumentsDeclaration);
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

    public FunctionInfo getFunctionInfo() {
        return functionInfo;
    }
}
