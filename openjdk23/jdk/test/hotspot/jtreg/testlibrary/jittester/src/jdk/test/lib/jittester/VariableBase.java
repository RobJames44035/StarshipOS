/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package jdk.test.lib.jittester;

public abstract class VariableBase extends IRNode {
    private final VariableInfo variableInfo;
    protected VariableBase(VariableInfo variableInfo) {
        super(variableInfo.type);
        this.variableInfo = variableInfo;
    }

    public VariableInfo getVariableInfo() {
        return variableInfo;
    }
}
