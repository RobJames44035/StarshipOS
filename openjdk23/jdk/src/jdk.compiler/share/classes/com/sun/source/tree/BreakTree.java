/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package com.sun.source.tree;

import javax.lang.model.element.Name;

/**
 * A tree node for a {@code break} statement.
 *
 * For example:
 * <pre>
 *   break;
 *
 *   break <em>label</em> ;
 * </pre>
 *
 * @jls 14.15 The break Statement
 *
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 * @since 1.6
 */
public interface BreakTree extends StatementTree {
    /**
     * Returns the label for this {@code break} statement.
     * @return the label
     */
    Name getLabel();

}
