/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package package1.package3;

public class Class3 {
    public void testAssert(boolean assertsShouldBeOn) {
        boolean assertsEnabled = false;
        assert(assertsEnabled = true);
        if (assertsEnabled != assertsShouldBeOn)
            throw new RuntimeException("Failure of Asserts Facility.");

        Class3 anonTest =  new Class3() {
            public void testAssert(boolean assertsShouldBeOn) {
                boolean assertsEnabled = false;
                assert(assertsEnabled = true);
                if (assertsEnabled != assertsShouldBeOn)
                    throw new RuntimeException("Failure of Asserts Facility.");
            }
        };
        anonTest.testAssert(assertsShouldBeOn);
    }

    // Named inner class
    public static class Class31 {
        public static void testAssert(boolean assertsShouldBeOn) {
            boolean assertsEnabled = false;
            assert(assertsEnabled = true);
            if (assertsEnabled != assertsShouldBeOn)
                throw new RuntimeException("Failure of Asserts Facility.");
        }
    }
}
