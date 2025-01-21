/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

// key: compiler.err.pattern.expected

class PatternSwitch {
    private void doSwitch(Object o) {
        switch (o) {
            case String: break;
            default: break;
        }
    }
}
