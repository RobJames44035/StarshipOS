/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

/**
 * Denote an instruction that may consume a value from the stack.
 */
public interface StackConsumer {

    /**
     * @return how many words are consumed from stack
     */
    int consumeStack(ConstantPoolGen cpg);
}
