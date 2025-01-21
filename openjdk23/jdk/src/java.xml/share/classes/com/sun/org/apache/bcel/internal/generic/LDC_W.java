/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

import java.io.IOException;

import com.sun.org.apache.bcel.internal.util.ByteSequence;

/**
 * LDC_W - Push item from constant pool (wide index)
 *
 * <PRE>
 * Stack: ... -&gt; ..., item.word1, item.word2
 * </PRE>
 */
public class LDC_W extends LDC {

    /**
     * Empty constructor needed for Instruction.readInstruction. Not to be used otherwise.
     */
    LDC_W() {
    }

    public LDC_W(final int index) {
        super(index);
    }

    /**
     * Read needed data (i.e., index) from file.
     */
    @Override
    protected void initFromFile(final ByteSequence bytes, final boolean wide) throws IOException {
        setIndex(bytes.readUnsignedShort());
        // Override just in case it has been changed
        super.setOpcode(com.sun.org.apache.bcel.internal.Const.LDC_W);
        super.setLength(3);
    }
}
