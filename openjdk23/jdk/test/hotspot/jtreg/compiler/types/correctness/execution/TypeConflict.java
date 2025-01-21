/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package compiler.types.correctness.execution;

import compiler.types.correctness.hierarchies.TypeHierarchy;
import compiler.types.correctness.scenarios.Scenario;

/**
 *  Type profiling conflict execution scenario. The main goal is
 *  to make compiler profile and compile methods with different types.
 *  Scenario tests guards by passing conflicting types (incompatible
 *  for the profiled data).
 */
public class TypeConflict<T extends TypeHierarchy.I, R> implements Execution<T, R> {
    /** Test methods execution number to make profile  */
    private final static int POLLUTION_THRESHOLD = 5000;
    /** Test methods execution number to make it profiled and compiled*/
    private final static int PROFILE_THRESHOLD = 20000;

    @Override
    public void execute(Scenario<T, R> scenario) {
        T base = scenario.getProfiled();
        T incompatible = scenario.getConflict();

        // pollute profile by passing different types
        R baseResult = null;
        R incResult = null;
        for (int i = 0; i < POLLUTION_THRESHOLD; i++) {
            baseResult = methodNotToCompile(scenario, base);
            incResult = methodNotToCompile(scenario, incompatible);
        }
        scenario.check(baseResult, base);
        scenario.check(incResult, incompatible);

        // profile and compile
        R result = null;
        for (int i = 0; i < PROFILE_THRESHOLD; i++) {
            result = methodNotToCompile(scenario, base);
        }
        scenario.check(result, base);

        // pass another type to make guard work and recompile
        for (int i = 0; i < PROFILE_THRESHOLD; i++) {
            result = methodNotToCompile(scenario, incompatible);
        }
        scenario.check(result, incompatible);
    }

    private R methodNotToCompile(Scenario<T, R> scenario, T t) {
        return scenario.run(t);
    }
}

