/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class T6394563 {
    void useDeprecated() {
        deprecated.foo();
    }
}

class deprecated {
    /** @deprecated */ static void foo() { }
}
