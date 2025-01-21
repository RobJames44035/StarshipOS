/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class performs matching on a collection of {@link Matchable} objects. It returns a list of {@link MatchResult}
 * objects which failed or an empty list of there was not failure.
 */
public class MatchableMatcher {
    private final Collection<? extends Matchable> matchables;

    public MatchableMatcher(Collection<? extends Matchable> matchables) {
        this.matchables = matchables;
    }

    public List<MatchResult> match() {
        List<MatchResult> results = new ArrayList<>();
        for (Matchable matchable : matchables) {
            MatchResult matchResult = matchable.match();
            if (matchResult.fail()) {
                results.add(matchResult);
            }
        }
        return results;
    }
}
