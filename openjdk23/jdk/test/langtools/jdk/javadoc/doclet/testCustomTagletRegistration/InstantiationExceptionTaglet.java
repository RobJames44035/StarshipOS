/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.Element;

import com.sun.source.doctree.DocTree;
import jdk.javadoc.doclet.Taglet;

public abstract class InstantiationExceptionTaglet implements Taglet {

    @Override
    public Set<Taglet.Location> getAllowedLocations() {
        return EnumSet.allOf(Taglet.Location.class);
    }

    @Override
    public boolean isInlineTag() {
        return false;
    }

    @Override
    public String getName() {
        return "InstantiationExceptionTaglet";
    }

    @Override
    public String toString(List<? extends DocTree> tags, Element element) {
        return "";
    }
}
