/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.cant.resolve

class CantResolve {
    Object o = new Object() {
        int i = f;
    };
}
