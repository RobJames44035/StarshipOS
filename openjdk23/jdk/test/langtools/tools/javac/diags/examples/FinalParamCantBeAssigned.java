/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.final.parameter.may.not.be.assigned

class FinalParamCantBeAssigned {
    void m(final int i) {
        i++;
    }
}
