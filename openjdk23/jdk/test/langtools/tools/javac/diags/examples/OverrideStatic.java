/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.override.static
// key: compiler.misc.cant.override

class Base {
    void m() { }
}

class OverrideStatic extends Base {
    static void m() { }
}
