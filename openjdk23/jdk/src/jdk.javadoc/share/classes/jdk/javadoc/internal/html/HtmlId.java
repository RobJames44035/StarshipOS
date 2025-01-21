/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package jdk.javadoc.internal.html;

/**
 * A type-safe wrapper around a {@code String}, for use as an "id"
 * in {@code HtmlTree} objects.
 *
 * @see HtmlTree#setId(HtmlId)
 */
public interface HtmlId {
    /**
     * Creates an id with the given name.
     *
     * @param name the name
     * @return the id
     */
    static HtmlId of(String name) {
        assert name.indexOf(' ') == -1;
        return () -> name;
    }

    /**
     * {@return the name of the id}
     */
    String name();
}
