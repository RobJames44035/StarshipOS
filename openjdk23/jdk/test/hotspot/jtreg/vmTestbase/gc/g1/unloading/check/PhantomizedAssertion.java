/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package gc.g1.unloading.check;

/**
 * This assertions checks that classloader will be phantomized.
 */
public class PhantomizedAssertion extends Assertion {

    private boolean phantomized = false;

    public void setPhantomized() {
        phantomized = true;
    }

    @Override
    public void check() {
        if (!phantomized) {
            throw new RuntimeException("Failing test! Object wasn't phantomized!");
        }
    }

}
