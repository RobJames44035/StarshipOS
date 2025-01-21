/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package com.sun.source.tree;

import java.util.List;

/**
 * Represents the package declaration.
 *
 * @jls 7.3 Compilation Units
 * @jls 7.4 Package Declarations
 *
 * @author Paul Govereau
 * @since 9
 */
public interface PackageTree extends Tree {
    /**
     * Returns the annotations associated with this package declaration.
     * @return the annotations
     */
    List<? extends AnnotationTree> getAnnotations();

    /**
     * Returns the name of the package being declared.
     * @return the name
     */
    ExpressionTree getPackageName();
}
