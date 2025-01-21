/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * This file is available under and governed by the GNU General Public
 * License version 2 only, as published by the Free Software Foundation.
 * However, a notice that is now available elsewhere in this distribution
 * accompanied the original version of this file, and, per its terms,
 * should not be removed.
 */

package jdk.internal.org.commonmark.renderer.markdown;

import jdk.internal.org.commonmark.renderer.NodeRenderer;

import java.util.Set;

/**
 * Factory for instantiating new node renderers for rendering custom nodes.
 */
public interface MarkdownNodeRendererFactory {

    /**
     * Create a new node renderer for the specified rendering context.
     *
     * @param context the context for rendering (normally passed on to the node renderer)
     * @return a node renderer
     */
    NodeRenderer create(MarkdownNodeRendererContext context);

    /**
     * @return the additional special characters that this factory would like to have escaped in normal text; currently
     * only ASCII characters are allowed
     */
    Set<Character> getSpecialCharacters();
}
