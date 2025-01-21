/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

/**
 * Super class for JSR - Jump to subroutine
 */
public abstract class JsrInstruction extends BranchInstruction implements UnconditionalBranch, TypedInstruction, StackProducer {

    /**
     * Empty constructor needed for Instruction.readInstruction. Not to be used otherwise.
     */
    JsrInstruction() {
    }

    JsrInstruction(final short opcode, final InstructionHandle target) {
        super(opcode, target);
    }

    /**
     * @return return address type
     */
    @Override
    public Type getType(final ConstantPoolGen cp) {
        return new ReturnaddressType(physicalSuccessor());
    }

    /**
     * Returns an InstructionHandle to the physical successor of this JsrInstruction. <B>For this method to work, this
     * JsrInstruction object must not be shared between multiple InstructionHandle objects!</B> Formally, there must not be
     * InstructionHandle objects i, j where i != j and i.getInstruction() == this == j.getInstruction().
     *
     * @return an InstructionHandle to the "next" instruction that will be executed when RETurned from a subroutine.
     */
    public InstructionHandle physicalSuccessor() {
        InstructionHandle ih = super.getTarget();
        // Rewind!
        while (ih.getPrev() != null) {
            ih = ih.getPrev();
        }
        // Find the handle for "this" JsrInstruction object.
        while (ih.getInstruction() != this) {
            ih = ih.getNext();
        }
        final InstructionHandle toThis = ih;
        while (ih != null) {
            ih = ih.getNext();
            if (ih != null && ih.getInstruction() == this) {
                throw new IllegalStateException("physicalSuccessor() called on a shared JsrInstruction.");
            }
        }
        // Return the physical successor
        return toThis.getNext();
    }
}
