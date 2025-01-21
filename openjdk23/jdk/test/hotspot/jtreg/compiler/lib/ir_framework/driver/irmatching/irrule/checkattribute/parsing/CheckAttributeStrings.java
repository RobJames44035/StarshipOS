/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching.irrule.checkattribute.parsing;

import compiler.lib.ir_framework.CompilePhase;
import compiler.lib.ir_framework.IR;
import compiler.lib.ir_framework.IRNode;
import compiler.lib.ir_framework.driver.irmatching.irrule.checkattribute.parsing.action.ConstraintAction;
import compiler.lib.ir_framework.driver.irmatching.irrule.checkattribute.parsing.action.CreateRawConstraintAction;
import compiler.lib.ir_framework.driver.irmatching.irrule.checkattribute.parsing.action.DefaultPhaseConstraintAction;
import compiler.lib.ir_framework.driver.irmatching.irrule.constraint.raw.RawConstraint;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class parses the check attribute strings of ({@link IR#failOn()} or {@link IR#counts()}) as found in a
 * {@link IR @IR} annotation without replacing placeholder IR strings, yet. For each constraint (i.e. all consecutive
 * strings in the check attribute that eventually form a constraint) we apply a {@link ConstraintAction} which defines
 * the action that is performed for the constraint.
 *
 * @see IR#failOn()
 * @see IR#counts()
 * @see ConstraintAction
 */
 public class CheckAttributeStrings {
    private final String[] checkAttributeStrings;

    public CheckAttributeStrings(String[] checkAttributeStrings) {
        this.checkAttributeStrings = checkAttributeStrings;
    }

    /**
     * Walk over the check attribute strings as found in the {@link IR} annotation and create {@link RawConstraint}
     * objects for them. Return them in a list.
     */
    public final List<RawConstraint> createRawConstraints(CreateRawConstraintAction createRawConstraintAction) {
        CheckAttributeReader<RawConstraint> reader = new CheckAttributeReader<>(checkAttributeStrings,
                                                                                createRawConstraintAction);
        List<RawConstraint> rawConstraints = new ArrayList<>();
        reader.read(rawConstraints);
        return rawConstraints;
    }

    /**
     * Walk over the check attribute strings as found in the {@link IR annotation} and return the default phase for
     * {@link IRNode} constraints.
     */
    public final Set<CompilePhase> parseDefaultCompilePhases(DefaultPhaseConstraintAction defaultPhaseConstraintAction) {
        Set<CompilePhase> compilePhases = new HashSet<>();
        CheckAttributeReader<CompilePhase> reader = new CheckAttributeReader<>(checkAttributeStrings,
                                                                               defaultPhaseConstraintAction);
        reader.read(compilePhases);
        return compilePhases;
    }
}
