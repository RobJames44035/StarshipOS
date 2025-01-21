/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching;

import compiler.lib.ir_framework.CompilePhase;
import compiler.lib.ir_framework.TestFramework;
import compiler.lib.ir_framework.driver.irmatching.irmethod.IRMethod;

import java.util.Map;

/**
 * This class provides information about the compilation output of a compile phase for an {@link IRMethod}.
 */
public class Compilation {
    private final Map<CompilePhase, String> compilationOutputMap;

    public Compilation(Map<CompilePhase, String> compilationOutputMap) {
        this.compilationOutputMap = compilationOutputMap;
    }

    /**
     * Is there a compilation output for {@code compilePhase}?
     */
    public boolean hasOutput(CompilePhase compilePhase) {
        return compilationOutputMap.containsKey(compilePhase);
    }

    /**
     * Get the compilation output for non-default compile phase {@code phase} or an empty string if no output was found
     * in the hotspot_pid* file for this compile phase.
     */
    public String output(CompilePhase compilePhase) {
        TestFramework.check(compilePhase != CompilePhase.DEFAULT, "cannot query for DEFAULT");
        return compilationOutputMap.getOrDefault(compilePhase, "");
    }
}
