/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

package com.sun.source.tree;

/**
 * A tree node for a 'requires' directive in a module declaration.
 *
 * For example:
 * <pre>
 *    requires <em>module-name</em>;
 *    requires static <em>module-name</em>;
 *    requires transitive <em>module-name</em>;
 * </pre>
 *
 * @since 9
 */
public interface RequiresTree extends DirectiveTree {
    /**
     * Returns true if this is a "requires static" directive.
     * @return true if this is a "requires static" directive
     */
    boolean isStatic();

    /**
     * Returns true if this is a "requires transitive" directive.
     * @return true if this is a "requires transitive" directive
     */
    boolean isTransitive();

    /**
     * Returns the name of the module that is required.
     * @return the name of the module that is required
     */
    ExpressionTree getModuleName();
}
