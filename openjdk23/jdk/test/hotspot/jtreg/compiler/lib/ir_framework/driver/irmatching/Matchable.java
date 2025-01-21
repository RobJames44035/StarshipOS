/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching;

/**
 * This interface is implemented by all classes on which an IR matching request can be performed.
 */
public interface Matchable {
    /**
     * Apply matching on this IR matching entity class.
     */
    MatchResult match();
}
