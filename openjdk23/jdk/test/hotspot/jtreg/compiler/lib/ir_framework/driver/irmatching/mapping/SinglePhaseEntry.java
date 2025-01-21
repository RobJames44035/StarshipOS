/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching.mapping;

import compiler.lib.ir_framework.CompilePhase;

/**
 * This class represents a mapping entry for an IR node that is only applicable for a single compile phase. This phase
 * also represents the default phase when the user specifies {@link CompilePhase#DEFAULT}.
 */
public class SinglePhaseEntry implements IRNodeMapEntry {
    private final SingleRegexEntry singleRegexEntry;

    public SinglePhaseEntry(CompilePhase defaultCompilePhase, String regex) {
        this.singleRegexEntry = new SingleRegexEntry(defaultCompilePhase, regex);
    }

    @Override
    public String regexForCompilePhase(CompilePhase compilePhase) {
        if (compilePhase == defaultCompilePhase()) {
            return singleRegexEntry.regex();
        } else {
            return null;
        }
    }

    @Override
    public CompilePhase defaultCompilePhase() {
        return singleRegexEntry.defaultCompilePhase();
    }
}
