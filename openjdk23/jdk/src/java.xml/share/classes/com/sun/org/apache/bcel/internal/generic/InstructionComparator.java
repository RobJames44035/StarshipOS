/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

/**
 * Equality of instructions isn't clearly to be defined. You might wish, for example, to compare whether instructions
 * have the same meaning. E.g., whether two INVOKEVIRTUALs describe the same call.
 * <p>
 * The DEFAULT comparator however, considers two instructions to be equal if they have same opcode and point to the same
 * indexes (if any) in the constant pool or the same local variable index. Branch instructions must have the same
 * target.
 * </p>
 *
 * @see Instruction
 */
public interface InstructionComparator {

    InstructionComparator DEFAULT = (i1, i2) -> {
        if (i1.getOpcode() == i2.getOpcode()) {
            if (i1 instanceof BranchInstruction) {
                // BIs are never equal to make targeters work correctly (BCEL-195)
                return false;
//                } else if (i1 == i2) { TODO consider adding this shortcut
//                    return true; // this must be AFTER the BI test
            }
            if (i1 instanceof ConstantPushInstruction) {
                return ((ConstantPushInstruction) i1).getValue().equals(((ConstantPushInstruction) i2).getValue());
            }
            if (i1 instanceof IndexedInstruction) {
                return ((IndexedInstruction) i1).getIndex() == ((IndexedInstruction) i2).getIndex();
            }
            if (i1 instanceof NEWARRAY) {
                return ((NEWARRAY) i1).getTypecode() == ((NEWARRAY) i2).getTypecode();
            }
            return true;
        }
        return false;
    };

    boolean equals(Instruction i1, Instruction i2);
}
