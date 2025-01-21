/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package package1;

public class Class1 {
    public void testAssert(boolean assertsShouldBeOn) {
        boolean assertsEnabled = false;
        assert assertsEnabled = true;
        if (assertsEnabled != assertsShouldBeOn)
            throw new RuntimeException("Failure of Asserts Facility.");

        Class1 anonTest =  new Class1() {
            public void testAssert(boolean assertsShouldBeOn) {
                boolean assertsEnabled2 = false;
                assert assertsEnabled2 = true;
                if (assertsEnabled2 != assertsShouldBeOn)
                    throw new RuntimeException("Failure of Asserts Facility.");
            }
        };
        anonTest.testAssert(assertsShouldBeOn);
    }

    // Named inner class
    public static class Class11 {
        public static void testAssert(boolean assertsShouldBeOn) {
            boolean assertsEnabled3 = false;
            assert assertsEnabled3 = true;
            if (assertsEnabled3 != assertsShouldBeOn)
                throw new RuntimeException("Failure of Asserts Facility.");
        }
    }

}
