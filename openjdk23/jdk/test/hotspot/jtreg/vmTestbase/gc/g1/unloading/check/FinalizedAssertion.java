/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package gc.g1.unloading.check;

/**
 * This assertion checks that classloader will be finalized.
 */
public class FinalizedAssertion extends Assertion {

    private boolean finalized = false;

    public void setFinalized() {
        finalized = true;
    }

    @Override
    public void check() {
        if (!finalized) {
            throw new RuntimeException("Failing test! Object wasn't finalized.");
        }
    }

}
