/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

/**
 * Denote an instruction that may throw a run-time or a linking exception (or both) during execution. This is not quite
 * the truth as such; because all instructions may throw an java.lang.VirtualMachineError. These exceptions are omitted.
 *
 * The Lava Language Specification specifies exactly which <i>RUN-TIME</i> and which <i>LINKING</i> exceptions each
 * instruction may throw which is reflected by the implementers. Due to the structure of the JVM specification, it may
 * be possible that an Instruction implementing this interface returns a Class[] of size 0.
 *
 * Please note that we speak of an "exception" here when we mean any "Throwable" object; so this term is equally used
 * for "Exception" and "Error" objects.
 */
public interface ExceptionThrower {

    Class<?>[] getExceptions();
}
