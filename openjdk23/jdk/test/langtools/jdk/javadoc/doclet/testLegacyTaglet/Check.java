/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.Element;

import com.sun.source.doctree.DocTree;
import jdk.javadoc.doclet.Taglet;

public class Check implements Taglet {

    private static final String TAG_NAME = "check";

    private final EnumSet<Location> allowedSet = EnumSet.allOf(Location.class);

    @Override
    public Set<Taglet.Location> getAllowedLocations() {
        return allowedSet;
    }

    /**
     * Return false since the tag is not an inline tag.
     *
     * @return false since the tag is not an inline tag.
     */
    @Override
    public boolean isInlineTag() {
        return false;
    }

    /**
     * Return the name of this custom tag.
     *
     * @return the name of this tag.
     */
    @Override
    public String getName() {
        return TAG_NAME;
    }

    /**
     * Given a list of DocTrees representing this custom tag, return its string
     * representation.
     *
     * @param tags the array of tags representing this custom tag.
     * @param element the declaration to which the enclosing comment belongs
     * @return null to test if the javadoc throws an exception or not.
     */
    @Override
    public String toString(List<? extends DocTree> tags, Element element) {
        return null;
    }
}
