/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package jdk.internal.classfile.impl;

import java.lang.classfile.CodeBuilder;
import java.lang.classfile.CodeModel;
import java.lang.classfile.CodeTransform;
import java.lang.classfile.MethodBuilder;
import java.lang.classfile.MethodElement;
import java.lang.classfile.constantpool.ConstantPoolBuilder;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

public final class ChainedMethodBuilder implements MethodBuilder {
    final TerminalMethodBuilder terminal;
    final Consumer<MethodElement> consumer;

    public ChainedMethodBuilder(MethodBuilder downstream,
                                Consumer<MethodElement> consumer) {
        this.consumer = consumer;
        this.terminal = switch (downstream) {
            case ChainedMethodBuilder cb -> cb.terminal;
            case TerminalMethodBuilder tb -> tb;
        };
    }

    @Override
    public MethodBuilder with(MethodElement element) {
        consumer.accept(requireNonNull(element));
        return this;
    }

    @Override
    public MethodBuilder withCode(Consumer<? super CodeBuilder> handler) {
        consumer.accept(terminal.bufferedCodeBuilder(null)
                                       .run(handler)
                                       .toModel());
        return this;
    }

    @Override
    public MethodBuilder transformCode(CodeModel code, CodeTransform transform) {
        BufferedCodeBuilder builder = terminal.bufferedCodeBuilder(code);
        builder.transform(code, transform);
        consumer.accept(builder.toModel());
        return this;
    }

    @Override
    public ConstantPoolBuilder constantPool() {
        return terminal.constantPool();
    }

}
