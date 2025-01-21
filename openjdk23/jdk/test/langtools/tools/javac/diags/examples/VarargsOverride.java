/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.misc.varargs.override
// key: compiler.warn.override.varargs.extra
// options: -Xlint:overrides

class Base {
    void m(Object... x) { }
}

class VarargsOverride extends Base {
    void m(Object[] x) { }
}
