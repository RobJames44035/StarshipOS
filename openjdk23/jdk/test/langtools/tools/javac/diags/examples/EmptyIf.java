/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.warn.empty.if
// options: -Xlint:empty

class EmptyIf {
    void m(int a, int b) {
        if (a == b) ;
    }
}
