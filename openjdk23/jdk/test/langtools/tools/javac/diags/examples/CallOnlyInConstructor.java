/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

// key: compiler.err.call.must.only.appear.in.ctor

class CallOnlyInConstructor {
    void foo() {
        super();
    }
}
