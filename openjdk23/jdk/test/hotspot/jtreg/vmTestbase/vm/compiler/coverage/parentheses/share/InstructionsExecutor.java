/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */
package vm.compiler.coverage.parentheses.share;

import java.util.List;

/**
 * Base interface for instructions executors:
 * classes that could execute list of VM instructions
 */
public interface InstructionsExecutor {
    public int execute(List<Instruction> instructions) throws Exception;
}
