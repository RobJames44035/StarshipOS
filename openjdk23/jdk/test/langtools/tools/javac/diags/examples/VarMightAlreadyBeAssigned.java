/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.var.might.already.be.assigned

class VarMightAlreadyBeAssigned {
    void m(boolean b) {
        final int i;
        if (b)
            i = 3;
        i = 4;
    }
}
