/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * This file is available under and governed by the GNU General Public
 * License version 2 only, as published by the Free Software Foundation.
 * However, a notice that is now available elsewhere in this distribution
 * accompanied the original version of this file, and, per its terms,
 * should not be removed.
 */

package jdk.internal.org.commonmark.text;

/**
 * Matcher interface for {@code char} values.
 * <p>
 * Note that because this matches on {@code char} values only (as opposed to {@code int} code points),
 * this only operates on the level of code units and doesn't support supplementary characters
 * (see {@link Character#isSupplementaryCodePoint(int)}).
 */
public interface CharMatcher {

    boolean matches(char c);
}
