/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package java.lang.classfile;

import java.lang.classfile.constantpool.ClassEntry;

import jdk.internal.classfile.impl.SuperclassImpl;

/**
 * Models the superclass of a class.  Delivered as a {@link
 * java.lang.classfile.ClassElement} when traversing a {@link ClassModel}.
 *
 * @since 24
 */
public sealed interface Superclass
        extends ClassElement
        permits SuperclassImpl {

    /** {@return the superclass} */
    ClassEntry superclassEntry();

    /**
     * {@return a {@linkplain Superclass} element}
     * @param superclassEntry the superclass
     */
    static Superclass of(ClassEntry superclassEntry) {
        return new SuperclassImpl(superclassEntry);
    }
}
