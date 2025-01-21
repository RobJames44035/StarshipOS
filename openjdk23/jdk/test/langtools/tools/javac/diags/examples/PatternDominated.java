/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

// key: compiler.err.pattern.dominated

class PatternDominated {
    private void doSwitch(Object o) {
        switch (o) {
            case CharSequence cs: break;
            case String str: break;
        }
    }
}
