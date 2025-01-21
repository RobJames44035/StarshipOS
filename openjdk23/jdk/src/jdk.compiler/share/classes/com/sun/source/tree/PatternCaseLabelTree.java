/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package com.sun.source.tree;

/**
 * A case label element that refers to an expression
 * @since 21
 */
public interface PatternCaseLabelTree extends CaseLabelTree {

    /**
     * The pattern for the case.
     *
     * @return the pattern
     */
    public PatternTree getPattern();

}
