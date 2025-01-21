/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching.irrule.checkattribute;

import compiler.lib.ir_framework.IR;
import compiler.lib.ir_framework.driver.irmatching.MatchResult;
import compiler.lib.ir_framework.driver.irmatching.Matchable;
import compiler.lib.ir_framework.driver.irmatching.irrule.constraint.Constraint;
import compiler.lib.ir_framework.driver.irmatching.irrule.constraint.SuccessResult;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * This class represents a fully parsed {@link IR#failOn()} attribute of an IR rule for a compile phase that is ready
 * to be IR matched on. This class provides a quick check regex by simply looking for any occurrence of any constraint
 * regex. Only if that fails, we need to check each constraint individually to report which one failed.
 *
 * @see IR#failOn()
 */
public class FailOn implements Matchable {
    private final Matchable checkAttribute;

    private final List<Constraint> constraints;
    private final String compilationOutput;

    public FailOn(List<Constraint> constraints, String compilationOutput) {
        this.checkAttribute = new CheckAttribute(CheckAttributeType.FAIL_ON, constraints);
        this.constraints = constraints;
        this.compilationOutput = compilationOutput;
    }

    @Override
    public MatchResult match() {
        if (hasNoMatchQuick()) {
            return SuccessResult.getInstance();
        }
        return checkAttribute.match();
    }

    /**
     * Quick check: Look for any occurrence of any regex by creating the following pattern to match against:
     * "regex_1|regex_2|...|regex_n"
     */
    private boolean hasNoMatchQuick() {
        String patternString = constraints.stream().map(Constraint::nodeRegex).collect(Collectors.joining("|"));
        Pattern pattern = Pattern.compile(String.join("|", patternString));
        Matcher matcher = pattern.matcher(compilationOutput);
        return !matcher.find();
    }
}
