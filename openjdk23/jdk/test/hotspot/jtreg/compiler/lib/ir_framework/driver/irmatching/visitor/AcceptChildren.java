/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching.visitor;

import compiler.lib.ir_framework.driver.irmatching.MatchResult;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * This class invokes {@link MatchResult#accept(MatchResultVisitor)} on all failed match results (i.e. children) inside
 * a {@link MatchResult} object to visit them.
 */
public class AcceptChildren implements Consumer<MatchResultVisitor> {
    private final Collection<? extends MatchResult> matchResults;

    public AcceptChildren(List<MatchResult> matchResults) {
        this.matchResults = matchResults;
    }

    @Override
    public void accept(MatchResultVisitor visitor) {
        for (MatchResult result : matchResults) {
            if (result.fail()) {
                result.accept(visitor);
            }
        }
    }
}
