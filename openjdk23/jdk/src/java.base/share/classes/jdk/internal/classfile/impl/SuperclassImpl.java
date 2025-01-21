/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package jdk.internal.classfile.impl;

import java.lang.classfile.Superclass;
import java.lang.classfile.constantpool.ClassEntry;

import static java.util.Objects.requireNonNull;

public final class SuperclassImpl
        extends AbstractElement
        implements Superclass {
    private final ClassEntry superclassEntry;

    public SuperclassImpl(ClassEntry superclassEntry) {
        requireNonNull(superclassEntry);
        this.superclassEntry = superclassEntry;
    }

    @Override
    public ClassEntry superclassEntry() {
        return superclassEntry;
    }

    @Override
    public void writeTo(DirectClassBuilder builder) {
        builder.setSuperclass(superclassEntry);
    }

    @Override
    public String toString() {
        return String.format("Superclass[superclassEntry=%s]", superclassEntry.name().stringValue());
    }
}
