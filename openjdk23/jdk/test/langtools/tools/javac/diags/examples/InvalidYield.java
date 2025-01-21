/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

// key: compiler.err.invalid.yield

class BreakComplexValueNoSwitchExpressions {
    void t() {
        while (true) {
            yield(1, 2);
        }
    }
    private void yield(int i, int j) {}
}
