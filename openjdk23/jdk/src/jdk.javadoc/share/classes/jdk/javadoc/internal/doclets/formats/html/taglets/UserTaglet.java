/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package jdk.javadoc.internal.doclets.formats.html.taglets;

import java.util.List;
import java.util.Set;

import javax.lang.model.element.Element;

import com.sun.source.doctree.DocTree;

import jdk.javadoc.internal.html.Content;
import jdk.javadoc.internal.html.RawHtml;

/**
 * A taglet wrapper, allows the public taglet {@link jdk.javadoc.doclet.Taglet}
 * wrapped into an internal {@code Taglet} representation.
 */
public final class UserTaglet implements Taglet {

    private final jdk.javadoc.doclet.Taglet userTaglet;

    UserTaglet(jdk.javadoc.doclet.Taglet t) {
        userTaglet = t;
    }

    @Override
    public Set<jdk.javadoc.doclet.Taglet.Location> getAllowedLocations() {
        return userTaglet.getAllowedLocations();
    }

    @Override
    public boolean isInlineTag() {
        return userTaglet.isInlineTag();
    }

    @Override
    public boolean isBlockTag() {
        return userTaglet.isBlockTag();
    }

    @Override
    public String getName() {
        return userTaglet.getName();
    }

    @Override
    public Content getInlineTagOutput(Element element, DocTree tag, TagletWriter tagletWriter) {
        Content output = tagletWriter.getOutputInstance();
        output.add(RawHtml.of(userTaglet.toString(List.of(tag), element)));
        return output;
    }

    @Override
    public Content getAllBlockTagOutput(Element holder, TagletWriter tagletWriter) {
        Content output = tagletWriter.getOutputInstance();
        var utils = tagletWriter.utils;
        List<? extends DocTree> tags = utils.getBlockTags(holder, getName());
        if (!tags.isEmpty()) {
            String tagString = userTaglet.toString(tags, holder);
            if (tagString != null) {
                output.add(RawHtml.of(tagString));
            }
        }
        return output;
    }
}
