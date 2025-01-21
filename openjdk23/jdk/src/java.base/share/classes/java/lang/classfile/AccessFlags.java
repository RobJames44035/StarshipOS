/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package java.lang.classfile;

import java.lang.reflect.AccessFlag;
import java.util.Set;

import jdk.internal.classfile.impl.AccessFlagsImpl;

/**
 * Models the access flags for a class, method, or field.  Delivered as a
 * {@link ClassElement}, {@link FieldElement}, or {@link MethodElement}
 * when traversing the corresponding model type.
 *
 * @since 24
 */
public sealed interface AccessFlags
        extends ClassElement, MethodElement, FieldElement
        permits AccessFlagsImpl {

    /**
     * {@return the access flags, as a bit mask}
     */
    int flagsMask();

    /**
     * {@return the access flags}
     */
    Set<AccessFlag> flags();

    /**
     * {@return whether the specified flag is present}  The specified flag
     * should be a valid flag for the classfile location associated with this
     * element otherwise false is returned.
     * @param flag the flag to test
     */
    boolean has(AccessFlag flag);

    /**
     * {@return the classfile location for this element, which is either class,
     * method, or field}
     */
    AccessFlag.Location location();
}
