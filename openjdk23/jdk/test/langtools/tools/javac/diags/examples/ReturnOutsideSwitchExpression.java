/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

// key: compiler.err.return.outside.switch.expression

class ReturnOutsideSwitchExpression {
    int t(int i) {
        return switch (i) {
            case 0: return -1;
            default: yield 0;
        };
    }
}
