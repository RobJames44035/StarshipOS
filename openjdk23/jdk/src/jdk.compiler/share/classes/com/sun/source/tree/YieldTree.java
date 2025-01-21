/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package com.sun.source.tree;

/**
 * A tree node for a {@code yield} statement.
 *
 * For example:
 * <pre>
 *   yield <em>expression</em> ;
 * </pre>
 *
 * @jls 14.21 The yield Statement
 *
 * @since 14
 */
public interface YieldTree extends StatementTree {

    /**
     * Returns the expression for this {@code yield} statement.
     *
     * @return the expression
     */
    ExpressionTree getValue();
}
