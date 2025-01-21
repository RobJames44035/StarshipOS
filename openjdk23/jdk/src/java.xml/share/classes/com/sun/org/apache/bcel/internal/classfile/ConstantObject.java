/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.classfile;

/**
 * This interface denotes those constants that have a "natural" value, such as ConstantLong, ConstantString, etc..
 *
 * @see Constant
 */
public interface ConstantObject {

    /**
     * @return object representing the constant, e.g., Long for ConstantLong
     */
    Object getConstantValue(ConstantPool cp);
}
