/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.lang.model.element.Element;

import com.sun.source.doctree.DocTree;
import com.sun.source.doctree.UnknownInlineTagTree;

import jdk.javadoc.doclet.Taglet;

/**
 * An inline taglet that generates output in a {@code <span>} element.
 */
public class SpanTaglet implements Taglet {
    @Override
    public String getName() {
        return "span";
    }

    @Override
    public Set<Location> getAllowedLocations() {
        return Set.of(Location.values());
    }

    @Override
    public boolean isInlineTag() {
        return true;
    }

    @Override
    public String toString(List<? extends DocTree> trees, Element e) {
        var children = ((UnknownInlineTagTree) trees.get(0)).getContent();
        return "<span>"
                + children.stream().map(DocTree::toString).collect(Collectors.joining())
                + "</span>";
    }
}
