/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

// key: compiler.err.switch.expression.empty

class BreakOutsideSwitchExpression {
    String t(E e) {
        return switch (e) {
        };
    }
    enum E {}
}
