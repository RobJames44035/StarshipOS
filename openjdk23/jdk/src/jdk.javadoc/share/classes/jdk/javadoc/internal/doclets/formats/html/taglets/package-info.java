/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/**
 * Classes used to build the output for documentation comment tags.
 *
 * Tags are either inline tags, meaning they can be used within a
 * sentence or phrase, or are block tags, meaning that they provide
 * additional details that follow the main description in a comment.
 * Taglets model that distinction.
 *
 * Inline tags are always processed individually, within the surrounding
 * context. In general, inline tags always generate some (non-empty) output,
 * even if the output is some form indicating an error. It is almost never
 * correct to not generate any output to place between the parts of the
 * comment that come before and after the tag in the underlying comment.
 *
 * Conversely, block tags of any given kind are always processed as a
 * group, even if they do not appear contiguously in the underlying comment.
 */
package jdk.javadoc.internal.doclets.formats.html.taglets;
