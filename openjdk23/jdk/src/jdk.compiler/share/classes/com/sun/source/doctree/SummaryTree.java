/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package com.sun.source.doctree;

import java.util.List;

/**
 * A tree node for an {@code @summary} inline tag.
 *
 * <pre>
 *    {&#064;summary text}
 * </pre>
 *
 * @since 10
 */
public interface SummaryTree extends InlineTagTree {
    /**
     * Returns the summary or the first line of a comment.
     * @return the summary text
     */
    List<? extends DocTree> getSummary();
}
