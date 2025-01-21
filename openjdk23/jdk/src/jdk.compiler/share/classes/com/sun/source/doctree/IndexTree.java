/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package com.sun.source.doctree;

import java.util.List;

/**
 * A tree node for an {@code @index} inline tag.
 *
 * <pre>
 *    {&#064;index keyword optional description}
 * </pre>
 *
 * @since 9
 */
public interface IndexTree extends InlineTagTree {
    /**
     * Returns the specified search term.
     * @return the search term
     */
    DocTree getSearchTerm();

    /**
     * Returns the description, if any.
     * @return the description
     */
    List<? extends DocTree> getDescription();
}
