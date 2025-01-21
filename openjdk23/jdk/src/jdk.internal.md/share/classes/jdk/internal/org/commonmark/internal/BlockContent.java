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

package jdk.internal.org.commonmark.internal;

class BlockContent {

    private final StringBuilder sb;

    private int lineCount = 0;

    public BlockContent() {
        sb = new StringBuilder();
    }

    public BlockContent(String content) {
        sb = new StringBuilder(content);
    }

    public void add(CharSequence line) {
        if (lineCount != 0) {
            sb.append('\n');
        }
        sb.append(line);
        lineCount++;
    }

    public String getString() {
        return sb.toString();
    }

}
