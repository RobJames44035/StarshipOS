/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package com.sun.source.doctree;

import java.util.List;

/**
 * A tree node for an {@code @deprecated} block tag.
 *
 * <pre>
 *    &#064;deprecated deprecated text
 * </pre>
 *
 * @since 1.8
 */
public interface DeprecatedTree extends BlockTagTree {
    /**
     * Returns the description explaining why an item is deprecated.
     * @return the description
     */
    List<? extends DocTree> getBody();
}
