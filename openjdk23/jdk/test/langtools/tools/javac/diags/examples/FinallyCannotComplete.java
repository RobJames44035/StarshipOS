/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.warn.finally.cannot.complete
// options: -Xlint:finally

class FinallyCannotComplete {
    void m() {
        try {
        } finally {
            throw new Error();
        }
    }
}
