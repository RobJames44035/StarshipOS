/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package com.sun.source.tree;

/**
 * A tree node for a type cast expression.
 *
 * For example:
 * <pre>
 *   ( <em>type</em> ) <em>expression</em>
 * </pre>
 *
 * @jls 15.16 Cast Expressions
 *
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 * @since 1.6
 */
public interface TypeCastTree extends ExpressionTree {
    /**
     * Returns the target type of the cast.
     * @return the cast
     */
    Tree getType();

    /**
     * Returns the expression being cast.
     * @return the expression
     */
    ExpressionTree getExpression();
}
