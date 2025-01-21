/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

package com.sun.tools.javap;

import java.lang.classfile.Instruction;


/*
 *  Write additional details for an instruction.
 *
 *  <p><b>This is NOT part of any supported API.
 *  If you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 */
public abstract class InstructionDetailWriter extends BasicWriter {
    public enum Kind {
        LOCAL_VARS("localVariables"),
        LOCAL_VAR_TYPES("localVariableTypes"),
        SOURCE("source"),
        STACKMAPS("stackMaps"),
        TRY_BLOCKS("tryBlocks"),
        TYPE_ANNOS("typeAnnotations");

        Kind(String option) {
            this.option = option;
        }

        final String option;
    }

    InstructionDetailWriter(Context context) {
        super(context);
    }

    abstract void writeDetails(int pc, Instruction instr);
    void flush(int pc) { }
}
