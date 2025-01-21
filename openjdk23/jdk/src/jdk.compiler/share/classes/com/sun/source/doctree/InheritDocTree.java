/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package com.sun.source.doctree;

/**
 * A tree node for an {@code @inheritDoc} inline tag.
 *
 * <pre>
 *    {&#064;inheritDoc}
 *    {&#064;inheritDoc supertype}
 * </pre>
 *
 * @apiNote
 * There is no requirement that the comment containing the tag and the comment
 * containing the inherited documentation should either be both Markdown comments
 * or both traditional (not Markdown) comments.
 *
 * @since 1.8
 */
public interface InheritDocTree extends InlineTagTree {

    /**
     * {@return the reference to a superclass or superinterface from which
     * to inherit documentation, or {@code null} if no reference was provided}
     *
     * @implSpec this implementation returns {@code null}.
     * @since 22
     */
    default ReferenceTree getSupertype() {
        return null;
    }
}
