/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package package2;

public class Class2 {
    public void testAssert(boolean assertsShouldBeOn) {
        boolean assertsEnabled = false;
        assert(assertsEnabled = true);
        if (assertsEnabled != assertsShouldBeOn)
            throw new RuntimeException("Failure of Asserts Facility.");
    }
}
