/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package com.sun.source.doctree;

import java.util.List;

/**
 * A tree node for an {@code @serialField} block tag.
 *
 * <pre>
 *    &#064;serialField field-name field-type field-description
 * </pre>
 *
 * @since 1.8
 */
public interface SerialFieldTree extends BlockTagTree {
    /**
     * Returns the name of the serial field.
     * @return the name of the serial field
     */
    IdentifierTree getName();

    /**
     * Returns the type of the serial field.
     * @return the type of the serial field
     */
    ReferenceTree getType();

    /**
     * Returns the description of the serial field.
     * @return the description of the serial field
     */
    List<? extends DocTree> getDescription();
}
