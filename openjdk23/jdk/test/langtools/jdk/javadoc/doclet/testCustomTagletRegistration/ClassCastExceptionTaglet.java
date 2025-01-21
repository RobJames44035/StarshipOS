/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.Element;

import com.sun.source.doctree.DocTree;
import jdk.javadoc.doclet.Taglet;

public class ClassCastExceptionTaglet /* does NOT implement jdk.javadoc.doclet.Taglet */ {

    public Set<Taglet.Location> getAllowedLocations() {
        return EnumSet.allOf(Taglet.Location.class);
    }

    public boolean isInlineTag() {
        return false;
    }

    public String getName() {
        return "ClassCastExceptionTaglet";
    }

    public String toString(List<? extends DocTree> tags, Element element) {
        return "";
    }
}
