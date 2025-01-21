/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

// key: compiler.err.local.classes.cant.extend.sealed
// key: compiler.err.sealed.class.must.have.subclasses
// key: compiler.misc.local

sealed class C {
    void m() {
        final class D extends C { }
    }
}

