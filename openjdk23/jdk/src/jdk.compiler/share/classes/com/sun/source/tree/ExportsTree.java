/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

package com.sun.source.tree;

import java.util.List;

/**
 * A tree node for an 'exports' directive in a module declaration.
 *
 * For example:
 * <pre>
 *    exports <em>package-name</em>;
 *    exports <em>package-name</em> to <em>module-name</em>;
 * </pre>
 *
 * @since 9
 */
public interface ExportsTree extends DirectiveTree {

    /**
     * Returns the name of the package to be exported.
     * @return  the name of the package to be exported
     */
    ExpressionTree getPackageName();

    /**
     * Returns the names of the modules to which the package is exported,
     * or null, if the package is exported to all modules.
     *
     * @return the names of the modules to which the package is exported, or null
     */
    List<? extends ExpressionTree> getModuleNames();
}
