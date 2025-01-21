/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

// key: compiler.warn.unreachable.catch

class UnreachableCatch {

    void test() {
        try {
            throw new java.io.FileNotFoundException();
        }
        catch(java.io.FileNotFoundException exc) { }
        catch(java.io.IOException exc) { } //unreachable
    }
}
