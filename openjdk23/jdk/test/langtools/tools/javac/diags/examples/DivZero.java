/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.warn.div.zero
// options: -Xlint:divzero

class DivZero {
    int m(int a) {
        return a / 0 ;
    }
}
