/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package java.lang.classfile;

import jdk.internal.classfile.impl.ClassFileVersionImpl;

/**
 * Models the classfile version information for a class.  Delivered as a {@link
 * java.lang.classfile.ClassElement} when traversing the elements of a {@link
 * ClassModel}.
 *
 * @since 24
 */
public sealed interface ClassFileVersion
        extends ClassElement
        permits ClassFileVersionImpl {
    /**
     * {@return the major classfile version}
     */
    int majorVersion();

    /**
     * {@return the minor classfile version}
     */
    int minorVersion();

    /**
     * {@return a {@link ClassFileVersion} element}
     * @param majorVersion the major classfile version
     * @param minorVersion the minor classfile version
     */
    static ClassFileVersion of(int majorVersion, int minorVersion) {
        return new ClassFileVersionImpl(majorVersion, minorVersion);
    }
}
