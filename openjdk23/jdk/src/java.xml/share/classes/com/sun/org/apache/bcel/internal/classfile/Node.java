/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.classfile;

/**
 * Denote class to have an accept method();
 */
public interface Node {

    void accept(Visitor obj);
}
