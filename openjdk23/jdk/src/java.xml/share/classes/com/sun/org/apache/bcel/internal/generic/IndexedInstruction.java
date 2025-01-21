/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

/**
 * Denote entity that refers to an index, e.g. local variable instructions, RET, CPInstruction, etc.
 */
public interface IndexedInstruction {

    int getIndex();

    void setIndex(int index);
}
