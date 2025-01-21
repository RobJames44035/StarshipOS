/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package com.sun.source.doctree;

import java.util.List;

/**
 * A tree node for an {@code @link} or {@code @linkplain} inline tag.
 *
 * <pre>
 *    {&#064;link reference label}
 *    {&#064;linkplain reference label}
 * </pre>
 *
 * @since 1.8
 */
public interface LinkTree extends InlineTagTree {
    /**
     * Returns the reference of the link.
     * @return the reference
     */
    ReferenceTree getReference();

    /**
     * Returns the label, if any, of the link.
     * @return the label
     */
    List<? extends DocTree> getLabel();
}
