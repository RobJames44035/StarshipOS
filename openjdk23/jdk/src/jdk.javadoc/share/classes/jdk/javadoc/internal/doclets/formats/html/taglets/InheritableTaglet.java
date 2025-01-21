/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package jdk.javadoc.internal.doclets.formats.html.taglets;


import java.util.List;

import javax.lang.model.element.Element;

import com.sun.source.doctree.DocTree;

/**
 * A taglet should implement this interface if it supports an {@code {@inheritDoc}}
 * tag or is automatically inherited if it is missing.
 */
public interface InheritableTaglet {

    /*
     * Called by InheritDocTaglet on an inheritable taglet to expand {@inheritDoc S}
     * found inside a tag corresponding to that taglet in a method (dst).
     *
     * When inheriting failed some assumption, or caused an error, the taglet
     * can return either of:
     *
     *   - new Output(null, null, List.of(), false)
     *   - new Output(null, null, List.of(), true)
     *
     * In the future, this could be reworked using some other mechanism,
     * such as throwing an exception.
     */
    Output inherit(Element dst, Element src, DocTree tag, boolean isFirstSentence);

    record Output(DocTree holderTag,
                  Element holder,
                  List<? extends DocTree> inlineTags,
                  boolean isValidInheritDocTag) {
    }
}
