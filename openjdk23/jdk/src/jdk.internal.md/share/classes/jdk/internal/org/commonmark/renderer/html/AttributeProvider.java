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

package jdk.internal.org.commonmark.renderer.html;

import jdk.internal.org.commonmark.node.Node;

import java.util.Map;

/**
 * Extension point for adding/changing attributes on HTML tags for a node.
 */
public interface AttributeProvider {

    /**
     * Set the attributes for a HTML tag of the specified node by modifying the provided map.
     * <p>
     * This allows to change or even remove default attributes. With great power comes great responsibility.
     * <p>
     * The attribute key and values will be escaped (preserving character entities), so don't escape them here,
     * otherwise they will be double-escaped.
     * <p>
     * This method may be called multiple times for the same node, if the node is rendered using multiple nested
     * tags (e.g. code blocks).
     *
     * @param node the node to set attributes for
     * @param tagName the HTML tag name that these attributes are for (e.g. {@code h1}, {@code pre}, {@code code}).
     * @param attributes the attributes, with any default attributes already set in the map
     */
    void setAttributes(Node node, String tagName, Map<String, String> attributes);

}
