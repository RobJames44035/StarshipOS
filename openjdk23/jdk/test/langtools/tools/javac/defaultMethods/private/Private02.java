/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */


public class Private02 {
    interface I {
        private void foo(String s); // Error: private method must declare body.
        private abstract void foo(int i, int j); // Error: private & abstract: bad combo
        void foo(int x); // OK.
        private I foo() { return null; } // OK.
        private void foo(int x) {} // Name clash.
    }
    interface J extends I {
        private J foo() { return null; } // OK.
    }
    interface K extends J {
        void foo(); // OK
    }
}
