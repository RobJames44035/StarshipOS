/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.warn.override.varargs.extra
// key: compiler.misc.varargs.override
// options: -Xlint:overrides

class Base {
    void m(Object... x) { }
}

class OverrideVarargsExtra extends Base {
    void m(Object[] x) { }
}
