/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package jdk.internal.classfile.impl;

import java.lang.classfile.CodeBuilder;
import java.lang.classfile.Label;
import java.lang.classfile.constantpool.ConstantPoolBuilder;

public abstract sealed class NonterminalCodeBuilder implements CodeBuilder
    permits ChainedCodeBuilder, BlockCodeBuilderImpl {
    protected final TerminalCodeBuilder terminal;
    protected final CodeBuilder parent;

    public NonterminalCodeBuilder(CodeBuilder parent) {
        this.parent = parent;
        this.terminal = switch (parent) {
            case NonterminalCodeBuilder cb -> cb.terminal;
            case TerminalCodeBuilder cb -> cb;
        };
    }

    @Override
    public int receiverSlot() {
        return terminal.receiverSlot();
    }

    @Override
    public int parameterSlot(int paramNo) {
        return terminal.parameterSlot(paramNo);
    }

    @Override
    public ConstantPoolBuilder constantPool() {
        return terminal.constantPool();
    }

    @Override
    public Label newLabel() {
        return terminal.newLabel();
    }
}
