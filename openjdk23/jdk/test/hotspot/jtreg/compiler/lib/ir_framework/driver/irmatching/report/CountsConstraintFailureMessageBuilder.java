/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching.report;

import compiler.lib.ir_framework.IR;
import compiler.lib.ir_framework.driver.irmatching.irrule.constraint.CountsConstraintFailure;
import compiler.lib.ir_framework.shared.Comparison;

/**
 * This class creates a failure message for a {@link IR#counts} constraint failure.
 */
public class CountsConstraintFailureMessageBuilder {
    private final ConstraintFailureMessageBuilder constrainFailureMessageBuilder;
    private final Comparison<Integer> comparison;
    private final int matchedNodesSize;
    private final Indentation indentation;

    public CountsConstraintFailureMessageBuilder(CountsConstraintFailure countsConstraintMatchResult,
                                                 Indentation indentation) {
        this.constrainFailureMessageBuilder = new ConstraintFailureMessageBuilder(countsConstraintMatchResult,
                                                                                  indentation);
        this.comparison = countsConstraintMatchResult.comparison();
        this.matchedNodesSize = countsConstraintMatchResult.matchedNodes().size();
        this.indentation = indentation;
    }

    public String build() {
        String header = constrainFailureMessageBuilder.buildConstraintHeader();
        indentation.add();
        String body = buildFailedComparisonMessage() + buildMatchedCountsNodesMessage();
        indentation.sub();
        return header + body;
    }

    private String buildFailedComparisonMessage() {
        String failedComparison = "[found] " + matchedNodesSize + " "
                                  + comparison.getComparator() + " " + comparison.getGivenValue() + " [given]";
        return indentation + "- Failed comparison: " + failedComparison + System.lineSeparator();
    }

    private String buildMatchedCountsNodesMessage() {
        if (matchedNodesSize == 0) {
            return buildEmptyNodeMatchesMessage();
        } else {
            return constrainFailureMessageBuilder.buildMatchedNodesMessage("Matched");
        }
    }

    private String buildEmptyNodeMatchesMessage() {
        return indentation + "- No nodes matched!" + System.lineSeparator();
    }
}
