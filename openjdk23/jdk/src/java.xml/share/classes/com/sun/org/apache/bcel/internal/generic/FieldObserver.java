/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

/**
 * Imnplement this interface if you're interested in changes to a FieldGen object and register yourself with
 * addObserver().
 */
public interface FieldObserver {

    void notify(FieldGen field);
}
