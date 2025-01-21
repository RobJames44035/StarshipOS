/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import java.lang.invoke.MethodHandles;

/**
 * TestHelpers
 *
 * @author Brian Goetz
 */
class TestHelpers {
    interface TestInterface {
        public static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

        public static final int sf = 3;

        static int sm(int  x) { return 0; }
        default int m(int x) { return 0; }
        private int pm(int x) { return 0; }
        private static int psm(int x) { return 0; }
    }

    static class TestSuperclass {
        public int m(int x) { return -1; }
    }

    static class TestClass extends TestSuperclass implements TestInterface {
        public static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

        static int sf;
        int f;

        public TestClass()  {}

        public static int sm(int x) { return x; }
        public int m(int x) { return x; }
        private static int psm(int x) { return x; }
        private int pm(int x) { return x; }
    }
}
