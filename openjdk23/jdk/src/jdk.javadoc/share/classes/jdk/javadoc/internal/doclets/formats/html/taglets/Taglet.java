/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package jdk.javadoc.internal.doclets.formats.html.taglets;

import java.util.Set;

import javax.lang.model.element.Element;

import com.sun.source.doctree.DocTree;

import jdk.javadoc.doclet.Taglet.Location;
import jdk.javadoc.internal.html.Content;
import jdk.javadoc.internal.html.HtmlTree;

/**
 * This is the taglet interface used internally within the doclet.
 *
 * The public {@link jdk.javadoc.doclet.Taglet} interface only supports
 * output to strings.   This interface supports structured output,
 * to {@link Content} objects, such as {@link HtmlTree}.
 *
 * User-provided taglets are supported using the {@link UserTaglet}
 * wrapper class.
 */
public interface Taglet {
    /**
     * Returns the set of allowed locations for a block tag handled by this taglet.
     *
     * @return the set of allowable locations
     */
    Set<Location> getAllowedLocations();

    /**
     * Indicates whether this {@code Taglet} can be used in field documentation.
     *
     * @return {@code true} if this {@code Taglet} can be used in field documentation
     *         and {@code false} otherwise
     */
    default boolean inField() {
        return getAllowedLocations().contains(Location.FIELD);
    }

    /**
     * Indicates whether this {@code Taglet} can be used in constructor documentation.
     *
     * @return {@code true} if this {@code Taglet} can be used in constructor documentation
     *         and {@code false} otherwise
     */
    default boolean inConstructor() {
        return getAllowedLocations().contains(Location.CONSTRUCTOR);
    }

    /**
     * Indicates whether this {@code Taglet} can be used in method documentation.
     *
     * @return {@code true} if this {@code Taglet} can be used in method documentation
     *         and {@code false} otherwise
     */
    default boolean inMethod() {
        return getAllowedLocations().contains(Location.METHOD);
    }

    /**
     * Indicates whether this {@code Taglet} can be used in overview documentation.
     *
     * @return {@code true} if this {@code Taglet} can be used in overview documentation
     *         and {@code false} otherwise
     */
    default boolean inOverview() {
        return getAllowedLocations().contains(Location.OVERVIEW);
    }

    /**
     * Indicates whether this {@code Taglet} can be used in module documentation.
     *
     * @return {@code true} if this {@code Taglet} can be used in module documentation
     *         and {@code false} otherwise
     */
    default boolean inModule()  {
        return getAllowedLocations().contains(Location.MODULE);
    }

    /**
     * Indicates whether this {@code Taglet} can be used in package documentation.
     *
     * @return {@code true} if this {@code Taglet} can be used in package documentation
     *         and {@code false} otherwise
     */
    default boolean inPackage() {
        return getAllowedLocations().contains(Location.PACKAGE);
    }

    /**
     * Indicates whether this {@code Taglet} can be used in type documentation (classes or interfaces).
     *
     * @return {@code true} if this {@code Taglet} can be used in type documentation
     *         and {@code false} otherwise
     */
    default boolean inType()  {
        return getAllowedLocations().contains(Location.TYPE);
    }

    /**
     * Indicates whether this {@code Taglet} represents an inline tag.
     *
     * @return {@code true} if this {@code Taglet} represents an inline tag
     *         and {@code false} otherwise
     */
    boolean isInlineTag();

    /**
     * Indicates whether this {@code Taglet} represents a block tag.
     *
     * @return {@code true} if this {@code Taglet} represents a block tag
     * @implSpec This implementation returns the inverse
     * result to {@code isInlineTag}.
     */
    default boolean isBlockTag() {
        return !isInlineTag();
    }

    /**
     * Returns the name of this tag.
     * @return the name of this tag
     */
    String getName();

    /**
     * Returns the content to be included in the generated output for an
     * instance of an inline tag handled by this taglet.
     *
     * @param owner  the element for the enclosing doc comment
     * @param tag    the tag
     * @param tagletWriter the taglet-writer used in this doclet
     *
     * @return the output for this tag
     * @throws UnsupportedTagletOperationException if the method is not supported by the taglet
     */
    Content getInlineTagOutput(Element owner, DocTree tag, TagletWriter tagletWriter) throws
            UnsupportedTagletOperationException;

    /**
     * Returns the content to be included in the generated output for
     * all instances of block tags handled by this taglet.
     *
     * @param owner  the element for the enclosing doc comment
     * @param tagletWriter the taglet-writer used in this doclet
     *
     * @return the output for this tag
     * @throws UnsupportedTagletOperationException if the method is not supported by the taglet
     */
    Content getAllBlockTagOutput(Element owner, TagletWriter tagletWriter) throws
            UnsupportedTagletOperationException;

    class UnsupportedTagletOperationException extends UnsupportedOperationException {
        private static final long serialVersionUID = -3530273193380250271L;
        public UnsupportedTagletOperationException(String message) {
            super(message);
        }
    }
}
