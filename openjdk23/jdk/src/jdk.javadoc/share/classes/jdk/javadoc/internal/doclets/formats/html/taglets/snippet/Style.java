/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package jdk.javadoc.internal.doclets.formats.html.taglets.snippet;

/**
 * A style of a snippet text character.
 */
public sealed interface Style {

    /**
     * A style that describes a link. Characters of this style are typically
     * processed by being wrapped into an HTML {@code A} element pointing to the
     * provided target.
     */
    record Link(String target) implements Style { }

    /**
     * A named style. Characters of this style are typically processed by
     * being wrapped into an HTML {@code SPAN} element with the {@code class}
     * attribute which is obtained from the provided name.
     */
    record Name(String name) implements Style { }

    /**
     * A marker of belonging to markup. Characters of this style are typically
     * processed by being omitted from the output.
     */
    record Markup() implements Style { }
}
