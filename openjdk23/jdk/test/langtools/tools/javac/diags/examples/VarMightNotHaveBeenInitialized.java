/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.var.might.not.have.been.initialized

class X {
    final int i;
    X(boolean b) {
        if (b)
            i = 3;
    }
}
