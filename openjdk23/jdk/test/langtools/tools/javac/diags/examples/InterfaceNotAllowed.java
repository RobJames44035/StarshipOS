/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.intf.not.allowed.here
// options: --release 15

class InterfaceNotAllowed {
    void m() {
        interface I { }
    }
}
