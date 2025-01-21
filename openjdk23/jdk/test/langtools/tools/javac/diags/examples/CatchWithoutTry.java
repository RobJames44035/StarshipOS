/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.catch.without.try

class CatchWithoutTry {
    void m() {
        catch (Exception e) {
        }
    }
}
