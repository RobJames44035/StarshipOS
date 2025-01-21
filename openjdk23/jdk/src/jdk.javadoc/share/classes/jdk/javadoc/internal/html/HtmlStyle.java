/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package jdk.javadoc.internal.html;

/**
 * An abstraction for the type-safe representation and use of CSS class names.
 *
 * @apiNote
 * Despite the name, implementations of this interface provide values for the HTML
 * {@code class} attribute, and <strong>not</strong> the HTML {@code style} attribute.
 * This is to avoid confusion with the widespread use of the word "class" in the Java ecosystem,
 * and the potential for clashes with methods called {@code setClass} instead of {@code setStyle}.
 *
 * @see HtmlTree#addStyle(HtmlStyle)
 * @see HtmlTree#setStyle(HtmlStyle)
 */
public interface HtmlStyle {
    String cssName();
}
