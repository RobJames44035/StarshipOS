/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package com.sun.source.doctree;

import java.util.List;

/**
 * A tree node for an {@code @uses} block tag.
 *
 * <pre>
 *    &#064;uses service-type description
 * </pre>
 *
 * @since 9
 */
public interface UsesTree extends BlockTagTree {
    /**
     * Returns the name of the service type being documented.
     * @return the name of the service type
     */
    ReferenceTree getServiceType();

    /**
     * Returns a description of the use of service type within the module.
     * @return the description
     */
    List<? extends DocTree> getDescription();
}
