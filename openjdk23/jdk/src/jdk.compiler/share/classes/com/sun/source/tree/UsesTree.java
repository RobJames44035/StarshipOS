
/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

package com.sun.source.tree;

/**
 * A tree node for a 'uses' directive in a module declaration.
 *
 * For example:
 * <pre>
 *    uses <em>service-name</em>;
 * </pre>
 *
 * @since 9
 */
public interface UsesTree extends DirectiveTree {
    /**
     * Returns the name of the service type.
     * @return  the name of the service type
     */
    ExpressionTree getServiceName();
}
