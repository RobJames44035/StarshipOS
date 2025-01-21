/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.abstract.cant.be.accessed.directly

abstract class Base {
    abstract void m();
}

class AbstractCantBeAccessed extends Base {
    void m() {
        super.m();
    }
}
