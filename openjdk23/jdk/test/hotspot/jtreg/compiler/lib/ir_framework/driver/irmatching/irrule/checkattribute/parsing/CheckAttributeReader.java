/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching.irrule.checkattribute.parsing;

import compiler.lib.ir_framework.IR;
import compiler.lib.ir_framework.IRNode;
import compiler.lib.ir_framework.driver.irmatching.irrule.checkattribute.parsing.action.ConstraintAction;
import compiler.lib.ir_framework.shared.TestFormat;

import java.util.*;

/**
 * This class reads the check attribute strings as found in ({@link IR#failOn()} or {@link IR#counts()}) and groups them
 * into constraints. For each constraint, a {@link ConstraintAction} is performed which creates an object. These objects
 * are then returned to the caller.
 *
 * @see IR#failOn()
 * @see IR#counts()
 * @see ConstraintAction
 */
public class CheckAttributeReader<R> {
    private final ListIterator<String> iterator;
    private final ConstraintAction<R> constraintAction;

    public CheckAttributeReader(String[] checkAttributeStrings, ConstraintAction<R> constraintAction) {
        this.iterator = Arrays.stream(checkAttributeStrings).toList().listIterator();
        this.constraintAction = constraintAction;
    }

    public void read(Collection<R> result) {
        int index = 1;
        while (iterator.hasNext()) {
            String node = iterator.next();
            CheckAttributeString userPostfix = readUserPostfix(node);
            RawIRNode rawIRNode = new RawIRNode(node, userPostfix);
            result.add(constraintAction.apply(iterator, rawIRNode, index++));
        }
    }

    public final CheckAttributeString readUserPostfix(String node) {
        if (IRNode.isCompositeIRNode(node)) {
            return readUserPostfixForCompositeIRNode(node);
        } else if (IRNode.isVectorIRNode(node)) {
            return readUserPostfixForVectorIRNode(node);
        } else {
            return CheckAttributeString.invalid();
        }
    }

    private final CheckAttributeString readUserPostfixForCompositeIRNode(String node) {
        String irNode = IRNode.getIRNodeAccessString(node);
        int nextIndex = iterator.nextIndex();
        TestFormat.checkNoReport(iterator.hasNext(), "Must provide additional value at index " +
                                                     nextIndex + " right after " + irNode);
        CheckAttributeString userPostfix = new CheckAttributeString(iterator.next());
        TestFormat.checkNoReport(userPostfix.isValidUserPostfix(), "Provided empty string for composite node " +
                                                                   irNode + " at index " + nextIndex);
        return userPostfix;
    }

    private final CheckAttributeString readUserPostfixForVectorIRNode(String node) {
        if (iterator.hasNext()) {
            String maybeVectorType = iterator.next();
            if (IRNode.isVectorSize(maybeVectorType)) {
                return new CheckAttributeString(maybeVectorType);
            }
            // If we do not find that pattern, then revert the iterator once
            iterator.previous();
        }
        return CheckAttributeString.invalid();
    }
}
