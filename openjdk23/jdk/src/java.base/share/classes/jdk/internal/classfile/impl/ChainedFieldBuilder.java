/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package jdk.internal.classfile.impl;

import java.lang.classfile.FieldBuilder;
import java.lang.classfile.FieldElement;
import java.lang.classfile.constantpool.ConstantPoolBuilder;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

public final class ChainedFieldBuilder implements FieldBuilder {
    private final TerminalFieldBuilder terminal;
    private final Consumer<FieldElement> consumer;

    public ChainedFieldBuilder(FieldBuilder downstream,
                               Consumer<FieldElement> consumer) {
        this.consumer = consumer;
        this.terminal = switch (downstream) {
            case ChainedFieldBuilder cb -> cb.terminal;
            case TerminalFieldBuilder tb -> tb;
        };
    }

    @Override
    public ConstantPoolBuilder constantPool() {
        return terminal.constantPool();
    }

    @Override
    public FieldBuilder with(FieldElement element) {
        consumer.accept(requireNonNull(element));
        return this;
    }

}

