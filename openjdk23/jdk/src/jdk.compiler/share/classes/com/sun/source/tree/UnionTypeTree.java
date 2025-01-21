/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

package com.sun.source.tree;

import java.util.List;

/**
 * A tree node for a union type expression in a multicatch
 * variable declaration.
 *
 * @author Maurizio Cimadamore
 *
 * @since 1.7
 */
public interface UnionTypeTree extends Tree {
    /**
     * Returns the alternative type expressions.
     * @return the alternative type expressions
     */
    List<? extends Tree> getTypeAlternatives();
}
