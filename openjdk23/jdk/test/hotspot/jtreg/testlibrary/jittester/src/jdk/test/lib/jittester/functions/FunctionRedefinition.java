/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package jdk.test.lib.jittester.functions;

import java.util.List;

import jdk.test.lib.jittester.IRNode;
import jdk.test.lib.jittester.visitors.Visitor;

public class FunctionRedefinition extends IRNode {
    private final FunctionInfo functionInfo;

    public FunctionRedefinition(FunctionInfo functionInfo,
                                   List<? extends ArgumentDeclaration> argumentsDeclaration, IRNode body, Return ret) {
        super(functionInfo.type);
        this.functionInfo = functionInfo;
        this.owner = functionInfo.owner;
        addChild(body);
        addChild(ret);
        addChildren(argumentsDeclaration);
    }

    @Override
    public long complexity() {
        IRNode body = getChild(0);
        IRNode ret = getChild(1);
        return body.complexity() + (ret != null ? ret.complexity() : 0);
    }

    @Override
    public<T> T accept(Visitor<T> v) {
        return v.visit(this);
    }

    public FunctionInfo getFunctionInfo() {
        return functionInfo;
    }
}
