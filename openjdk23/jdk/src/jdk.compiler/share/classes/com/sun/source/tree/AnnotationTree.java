/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package com.sun.source.tree;

import java.util.List;

/**
 * A tree node for an annotation.
 *
 * For example:
 * <pre>
 *    {@code @}<em>annotationType</em>
 *    {@code @}<em>annotationType</em> ( <em>arguments</em> )
 * </pre>
 *
 * @jls 9.7 Annotations
 *
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 * @since 1.6
 */
public interface AnnotationTree extends ExpressionTree {
    /**
     * Returns the annotation type.
     * @return the annotation type
     */
    Tree getAnnotationType();

    /**
     * Returns the arguments, if any, for the annotation.
     * @return the arguments for the annotation type
     */
    List<? extends ExpressionTree> getArguments();
}
