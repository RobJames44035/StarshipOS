/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package com.sun.source.tree;

import java.util.List;

/**
 * A tree node to stand in for a malformed expression.
 *
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 * @since 1.6
 */
public interface ErroneousTree extends ExpressionTree {
    /**
     * Returns any trees that were saved in this node.
     * @return the trees
     */
    List<? extends Tree> getErrorTrees();
}
