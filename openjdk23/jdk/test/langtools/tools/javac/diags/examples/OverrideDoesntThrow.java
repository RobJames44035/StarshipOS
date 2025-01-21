/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.override.meth.doesnt.throw
// key: compiler.misc.cant.override

class Base {
    void m() { }
}

class OverrideDoesntThrow extends Base {
    void m() throws Exception { }
}

