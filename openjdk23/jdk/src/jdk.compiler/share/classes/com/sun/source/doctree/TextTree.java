/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package com.sun.source.doctree;

/**
 * A tree node for plain text.
 *
 * @since 1.8
 */
public interface TextTree extends DocTree {
    /**
     * Returns the text.
     * @return the text
     */
    String getBody();
}
