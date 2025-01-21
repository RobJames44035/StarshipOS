/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package sun.reflect.generics.tree;

/**
 * Common superinterface for nodes that represent a (possibly generic)
 * type.
 * Corresponds to the production of the same name in the JVMS
 * section on signatures.
 */
public interface FieldTypeSignature
    extends BaseType, TypeSignature, TypeArgument {}
