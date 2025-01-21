/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching.irrule.checkattribute.parsing.action;

import compiler.lib.ir_framework.IR;
import compiler.lib.ir_framework.driver.irmatching.irrule.checkattribute.parsing.RawIRNode;
import compiler.lib.ir_framework.shared.Comparison;
import compiler.lib.ir_framework.shared.ComparisonConstraintParser;
import compiler.lib.ir_framework.shared.TestFormat;
import compiler.lib.ir_framework.shared.TestFormatException;

import java.util.ListIterator;

/**
 * This class represents a count string of a {@link IR} check attribute.
 */
class CountString {
    private final ListIterator<String> iterator;
    private final RawIRNode rawIRNode;

    public CountString(ListIterator<String> iterator, RawIRNode rawIRNode) {
        this.iterator = iterator;
        this.rawIRNode = rawIRNode;
    }

    public Comparison<Integer> parse() {
        TestFormat.checkNoReport(iterator.hasNext(), "Missing count for node " + rawIRNode.irNodePlaceholder());
        String countsString = iterator.next();
        try {
            return ComparisonConstraintParser.parse(countsString, CountString::parsePositiveInt);
        } catch (TestFormatException e) {
            String irNodeString = rawIRNode.irNodePlaceholder();
            throw new TestFormatException(e.getMessage() + ", node " + irNodeString + ", in count string \"" + countsString + "\"");
        }
    }

    public static int parsePositiveInt(String s) {
        int result = Integer.parseInt(s);
        if (result < 0) {
            throw new NumberFormatException("cannot be negative");
        }
        return result;
    }
}
