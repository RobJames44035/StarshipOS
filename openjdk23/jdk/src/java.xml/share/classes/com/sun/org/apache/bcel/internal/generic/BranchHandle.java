/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

/**
 * BranchHandle is returned by specialized InstructionList.append() whenever a BranchInstruction is appended. This is
 * useful when the target of this instruction is not known at time of creation and must be set later via setTarget().
 *
 * @see InstructionHandle
 * @see Instruction
 * @see InstructionList
 */
public final class BranchHandle extends InstructionHandle {

    /**
     * Factory method.
     */
    static BranchHandle getBranchHandle(final BranchInstruction i) {
        return new BranchHandle(i);
    }

    // This is also a cache in case the InstructionHandle#swapInstruction() method is used
    // See BCEL-273
    private BranchInstruction bi; // An alias in fact, but saves lots of casts

    private BranchHandle(final BranchInstruction i) {
        super(i);
        bi = i;
    }

    /*
     * Override InstructionHandle methods: delegate to branch instruction. Through this overriding all access to the private
     * i_position field should be prevented.
     */
    @Override
    public int getPosition() {
        return bi.getPosition();
    }

    /**
     * @return target of instruction.
     */
    public InstructionHandle getTarget() {
        return bi.getTarget();
    }

    /**
     * Set new contents. Old instruction is disposed and may not be used anymore.
     */
    @Override // This is only done in order to apply the additional type check; could be merged with super impl.
    public void setInstruction(final Instruction i) { // TODO could be package-protected?
        super.setInstruction(i);
        if (!(i instanceof BranchInstruction)) {
            throw new ClassGenException("Assigning " + i + " to branch handle which is not a branch instruction");
        }
        bi = (BranchInstruction) i;
    }

    @Override
    void setPosition(final int pos) {
        // Original code: i_position = bi.position = pos;
        bi.setPosition(pos);
        super.setPosition(pos);
    }

    /**
     * Pass new target to instruction.
     */
    public void setTarget(final InstructionHandle ih) {
        bi.setTarget(ih);
    }

    @Override
    protected int updatePosition(final int offset, final int maxOffset) {
        final int x = bi.updatePosition(offset, maxOffset);
        super.setPosition(bi.getPosition());
        return x;
    }

    /**
     * Update target of instruction.
     */
    public void updateTarget(final InstructionHandle oldIh, final InstructionHandle newIh) {
        bi.updateTarget(oldIh, newIh);
    }
}
