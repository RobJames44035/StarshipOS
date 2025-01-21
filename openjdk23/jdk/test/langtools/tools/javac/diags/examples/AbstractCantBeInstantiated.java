/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.abstract.cant.be.instantiated

class AbstractCantBeInstantiated {
    abstract class C { }

    void m() {
        new C();
    }
}
