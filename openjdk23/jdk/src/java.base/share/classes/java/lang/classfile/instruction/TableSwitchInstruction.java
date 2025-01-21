/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package java.lang.classfile.instruction;

import java.lang.classfile.CodeBuilder;
import java.lang.classfile.CodeElement;
import java.lang.classfile.CodeModel;
import java.lang.classfile.Instruction;
import java.lang.classfile.Label;
import java.lang.classfile.Opcode;
import java.util.List;

import jdk.internal.classfile.impl.AbstractInstruction;

/**
 * Models a {@link Opcode#TABLESWITCH tableswitch} instruction in the {@code code} array of a
 * {@code Code} attribute.  Delivered as a {@link CodeElement} when traversing
 * the elements of a {@link CodeModel}.
 * <p>
 * A table switch instruction is composite:
 * {@snippet lang=text :
 * // @link substring="TableSwitchInstruction" target="#of" :
 * TableSwitchInstruction(
 *     int lowValue, // @link substring="int lowValue" target="#lowValue"
 *     int highValue, // @link substring="int highValue" target="#highValue"
 *     Label defaultTarget, // @link substring="defaultTarget" target="#defaultTarget"
 *     List<SwitchCase> cases // @link substring="cases" target="#cases()"
 * )
 * }
 * <p>
 * When read from {@code class} files, the {@code cases} may omit cases that
 * duplicate the default target.  The list is sorted ascending by the {@link
 * SwitchCase#caseValue() caseValue}.
 * <p>
 * When writing to {@code class} file, the order in the {@code cases} list does
 * not matter, as there is only one valid order in the physical representation
 * of table switch entries.  Treatment of elements in {@code cases} whose value
 * is less than {@code lowValue} or greater than {@code highValue}, and elements
 * whose value duplicates that of another, is not specified.
 *
 * @see Opcode.Kind#TABLE_SWITCH
 * @see CodeBuilder#tableswitch CodeBuilder::tableswitch
 * @jvms 6.5.tableswitch <em>tableswitch</em>
 * @since 24
 */
public sealed interface TableSwitchInstruction extends Instruction
        permits AbstractInstruction.BoundTableSwitchInstruction, AbstractInstruction.UnboundTableSwitchInstruction {
    /**
     * {@return the low value of the switch target range, inclusive}
     */
    int lowValue();

    /**
     * {@return the high value of the switch target range, inclusive}
     */
    int highValue();

    /**
     * {@return the default target of the switch}
     */
    Label defaultTarget();

    /**
     * {@return the cases of the switch}
     */
    List<SwitchCase> cases();

    /**
     * {@return a table switch instruction}
     *
     * @param lowValue the low value of the switch target range, inclusive
     * @param highValue the high value of the switch target range, inclusive
     * @param defaultTarget the default target of the switch
     * @param cases the cases of the switch; duplicate or out of bound case
     *              handling is not specified
     */
    static TableSwitchInstruction of(int lowValue, int highValue, Label defaultTarget, List<SwitchCase> cases) {
        return new AbstractInstruction.UnboundTableSwitchInstruction(lowValue, highValue, defaultTarget, cases);
    }
}
