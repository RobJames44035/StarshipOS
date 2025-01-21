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

import jdk.internal.org.commonmark.parser.block.BlockContinue;

public class BlockContinueImpl extends BlockContinue {

    private final int newIndex;
    private final int newColumn;
    private final boolean finalize;

    public BlockContinueImpl(int newIndex, int newColumn, boolean finalize) {
        this.newIndex = newIndex;
        this.newColumn = newColumn;
        this.finalize = finalize;
    }

    public int getNewIndex() {
        return newIndex;
    }

    public int getNewColumn() {
        return newColumn;
    }

    public boolean isFinalize() {
        return finalize;
    }

}
