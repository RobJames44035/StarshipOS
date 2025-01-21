/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

/**
 * Get the type associated with an instruction, int for ILOAD, or the type of the field of a PUTFIELD instruction, e.g..
 */
public interface TypedInstruction {

    Type getType(ConstantPoolGen cpg);
}
