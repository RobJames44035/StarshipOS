/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package com.sun.source.doctree;

import javax.lang.model.element.Name;

/**
 * A tree node for the end of an HTML element.
 *
 * <pre>
 *    &lt;/ name &gt;
 * </pre>
 *
 * @since 1.8
 */
public interface EndElementTree extends DocTree {
    /**
     * Returns the name of this element.
     * @return the name
     */
    Name getName();
}
