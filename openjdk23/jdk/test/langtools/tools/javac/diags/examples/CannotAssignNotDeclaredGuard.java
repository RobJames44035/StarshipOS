/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

// key: compiler.err.cannot.assign.not.declared.guard

class CannotAssignNotDeclaredGuard {
    void test(Object i) {
        final boolean b;
        switch (i) {
            case Object o when b = true -> {}
            default -> {}
        }
    }
}
