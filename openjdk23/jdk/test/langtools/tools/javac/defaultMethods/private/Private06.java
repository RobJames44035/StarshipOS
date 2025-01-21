/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class Private06 {
    @FunctionalInterface
    interface NAFI {
        private void foo() {
        }
    }

    @FunctionalInterface
    interface FI {
        void foo(NAFI nafi);
    }

    public static void meth() {
        Private06.NAFI nafi = () -> {};
        Private06.FI fi = Private06.NAFI::foo; // OK.
    }
}

class Private06_01 {
    public static void meth() {
        Private06.FI fi = Private06.NAFI::foo; // NOT OK.
    }
}
