/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

import java.io.DataOutputStream;
import java.io.IOException;

import com.sun.org.apache.bcel.internal.util.ByteSequence;

/**
 * TABLESWITCH - Switch within given range of values, i.e., low..high
 *
 * @see SWITCH
 */
public class TABLESWITCH extends Select {

    /**
     * Empty constructor needed for Instruction.readInstruction. Not to be used otherwise.
     */
    TABLESWITCH() {
    }

    /**
     * @param match sorted array of match values, match[0] must be low value, match[match_length - 1] high value
     * @param targets where to branch for matched values
     * @param defaultTarget default branch
     */
    public TABLESWITCH(final int[] match, final InstructionHandle[] targets, final InstructionHandle defaultTarget) {
        super(com.sun.org.apache.bcel.internal.Const.TABLESWITCH, match, targets, defaultTarget);
        /* Alignment remainder assumed 0 here, until dump time */
        final short length = (short) (13 + getMatchLength() * 4);
        super.setLength(length);
        setFixedLength(length);
    }

    /**
     * Call corresponding visitor method(s). The order is: Call visitor methods of implemented interfaces first, then call
     * methods according to the class hierarchy in descending order, i.e., the most specific visitXXX() call comes last.
     *
     * @param v Visitor object
     */
    @Override
    public void accept(final Visitor v) {
        v.visitVariableLengthInstruction(this);
        v.visitStackConsumer(this);
        v.visitBranchInstruction(this);
        v.visitSelect(this);
        v.visitTABLESWITCH(this);
    }

    /**
     * Dump instruction as byte code to stream out.
     *
     * @param out Output stream
     */
    @Override
    public void dump(final DataOutputStream out) throws IOException {
        super.dump(out);
        final int matchLength = getMatchLength();
        final int low = matchLength > 0 ? super.getMatch(0) : 0;
        out.writeInt(low);
        final int high = matchLength > 0 ? super.getMatch(matchLength - 1) : 0;
        out.writeInt(high);
        for (int i = 0; i < matchLength; i++) {
            out.writeInt(setIndices(i, getTargetOffset(super.getTarget(i))));
        }
    }

    /**
     * Read needed data (e.g. index) from file.
     */
    @Override
    protected void initFromFile(final ByteSequence bytes, final boolean wide) throws IOException {
        super.initFromFile(bytes, wide);
        final int low = bytes.readInt();
        final int high = bytes.readInt();
        final int matchLength = high - low + 1;
        setMatchLength(matchLength);
        final short fixedLength = (short) (13 + matchLength * 4);
        setFixedLength(fixedLength);
        super.setLength((short) (fixedLength + super.getPadding()));
        super.setMatches(new int[matchLength]);
        super.setIndices(new int[matchLength]);
        super.setTargets(new InstructionHandle[matchLength]);
        for (int i = 0; i < matchLength; i++) {
            super.setMatch(i, low + i);
            super.setIndices(i, bytes.readInt());
        }
    }
}
