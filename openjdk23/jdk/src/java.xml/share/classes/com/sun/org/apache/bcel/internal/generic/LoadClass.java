/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

/**
 * Denotes that an instruction may start the process of loading and resolving the referenced class in the Virtual Machine.
 */
public interface LoadClass {

    /**
     * Returns the {@link ObjectType} of the referenced class or interface that may be loaded and resolved.
     *
     * @param cpg A ConstantPoolGen
     * @return object type that may be loaded or null if a primitive is referenced
     */
    ObjectType getLoadClassType(ConstantPoolGen cpg);

    /**
     * Returns the type associated with this instruction. LoadClass instances are always typed, but this type does not always refer to the type of the class or
     * interface that it possibly forces to load. For example, {@link GETFIELD} would return the type of the field and not the type of the class where the field
     * is defined. If no class is forced to be loaded, {@code null} is returned. An example for this is an {@link NEWARRAY} instruction that creates an
     * {@code int[][]}.
     *
     * @param cpg A ConstantPoolGen
     * @return the type associated with this instruction.
     * @see #getLoadClassType(ConstantPoolGen)
     */
    Type getType(ConstantPoolGen cpg);
}
