/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.misc.clashes.with
// key: compiler.err.override.incompatible.ret

interface Base {
    int m();
}

interface ClashesWith extends Base {
    String m();
}
