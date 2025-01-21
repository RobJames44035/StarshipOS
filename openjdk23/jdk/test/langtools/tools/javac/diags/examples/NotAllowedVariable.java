/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

// key: compiler.err.variable.not.allowed

class NotAllowedVariable {
    void t1() {
        if (true)
            int x = 0;
    }
}
