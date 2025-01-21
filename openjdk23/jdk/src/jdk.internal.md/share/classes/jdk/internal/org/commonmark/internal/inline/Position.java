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

package jdk.internal.org.commonmark.internal.inline;

/**
 * Position within a {@link Scanner}. This is intentionally kept opaque so as not to expose the internal structure of
 * the Scanner.
 */
public class Position {

    final int lineIndex;
    final int index;

    Position(int lineIndex, int index) {
        this.lineIndex = lineIndex;
        this.index = index;
    }
}
