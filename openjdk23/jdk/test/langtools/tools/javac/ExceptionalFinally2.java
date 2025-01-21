/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class ExceptionalFinally2 {
    static class E extends Exception {}

    public void t() throws E {}

    void f() {
        try {
            try {
                t();
            } finally {
                return;
            }
        } catch (E x) { // error: E can't be thrown in try block
        }
    }
}
