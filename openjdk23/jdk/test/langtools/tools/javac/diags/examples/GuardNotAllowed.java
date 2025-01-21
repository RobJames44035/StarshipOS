/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

// key: compiler.err.guard.not.allowed

class GuardNotAllowed {
    private void doSwitch(int i, boolean b) {
        switch (i) {
            case 0 when b -> {}
            default -> {}
        }
    }
}
