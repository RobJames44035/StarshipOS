/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package jdk.internal.classfile.impl;

import java.lang.classfile.BootstrapMethodEntry;
import java.lang.classfile.constantpool.ConstantPool;
import java.lang.classfile.constantpool.LoadableConstantEntry;
import java.lang.classfile.constantpool.MethodHandleEntry;
import java.util.List;

import static jdk.internal.classfile.impl.AbstractPoolEntry.MethodHandleEntryImpl;

public final class BootstrapMethodEntryImpl implements BootstrapMethodEntry {

    final int index;
    final int hash;
    private final ConstantPool constantPool;
    private final MethodHandleEntryImpl handle;
    private final List<LoadableConstantEntry> arguments;

    BootstrapMethodEntryImpl(ConstantPool constantPool, int bsmIndex, int hash,
                                 MethodHandleEntryImpl handle,
                                 List<LoadableConstantEntry> arguments) {
        this.index = bsmIndex;
        this.hash = hash;
        this.constantPool = constantPool;
        this.handle = handle;
        this.arguments = List.copyOf(arguments);
    }

    @Override
    public ConstantPool constantPool() {
        return constantPool;
    }

    @Override
    public MethodHandleEntry bootstrapMethod() {
        return handle;
    }

    @Override
    public List<LoadableConstantEntry> arguments() {
        return arguments;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BootstrapMethodEntry e
                && e.bootstrapMethod().equals(handle)
                && e.arguments().equals(arguments);
    }

    static int computeHashCode(MethodHandleEntryImpl handle,
                               List<? extends LoadableConstantEntry> arguments) {
        return (31 * handle.hashCode() + arguments.hashCode()) | AbstractPoolEntry.NON_ZERO;
    }

    @Override
    public int bsmIndex() { return index; }

    @Override
    public int hashCode() {
        return hash;
    }

    void writeTo(BufWriterImpl writer) {
        writer.writeIndex(bootstrapMethod());
        Util.writeListIndices(writer, arguments());
    }
}
