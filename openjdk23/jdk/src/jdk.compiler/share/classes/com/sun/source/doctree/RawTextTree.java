/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package com.sun.source.doctree;

/**
 * A tree node for a fragment of uninterpreted raw text content.
 *
 * <p>
 * The content may contain any text except that for
 * {@linkplain InlineTagTree inline tags}.
 *
 * <p>The format of the content is indicated by the {@linkplain #getKind() kind}
 * of the tree node.
 *
 * @apiNote
 * This class may be used to represent tree nodes containing
 * {@linkplain DocTree.Kind#MARKDOWN Markdown} text.
 * Such nodes will typically exist in a list of {@code DocTree} nodes,
 * along with other kinds of {@code DocTree} nodes, such as for inline tags.
 * When processing any such list, any non-Markdown nodes will be processed
 * recursively first, and then treated as opaque objects within the remaining
 * stream of Markdown nodes. Thus, the content of any non-Markdown nodes will
 * not affect how the Markdown nodes will be processed.
 *
 * @since 23
 */
public interface RawTextTree extends DocTree {
    /**
     * {@return the content}
     */
    String getContent();
}
