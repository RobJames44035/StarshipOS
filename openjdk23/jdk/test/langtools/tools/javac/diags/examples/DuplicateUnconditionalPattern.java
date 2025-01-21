/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

// key: compiler.err.duplicate.unconditional.pattern

class DuplicateUnconditionalPattern {
    private void doSwitch(Object o) {
        switch (o) {
            case Object obj: break;
            case Object obj: break;
        }
    }
}
