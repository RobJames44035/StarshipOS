/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package compiler.codecache.stress;

import jdk.test.lib.TimeLimitedRunner;
import jdk.test.lib.Utils;

public class CodeCacheStressRunner {
    private final Runnable action;

    public CodeCacheStressRunner(Runnable action) {
        this.action = action;
    }

    protected final void runTest() {
        Helper.startInfiniteLoopThread(action);
        try {
            // Adjust timeout and substract vm init and exit time
            new TimeLimitedRunner(60 * 1000, 2.0d, this::test).call();
        } catch (Exception e) {
            throw new Error("Exception occurred during test execution", e);
        }
    }

    private boolean test() {
        Helper.TestCase obj = Helper.TestCase.get();
        Helper.callMethod(obj.getCallable(), obj.expectedValue());
        return true;
    }

}
