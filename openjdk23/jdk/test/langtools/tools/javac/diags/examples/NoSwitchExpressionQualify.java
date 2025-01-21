/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

// key: compiler.err.no.switch.expression.qualify

class BreakComplexValueNoSwitchExpressions {
    void t() {
        while (true) {
            yield(1 + 1);
        }
    }
    private void yield(int i) {}
}
