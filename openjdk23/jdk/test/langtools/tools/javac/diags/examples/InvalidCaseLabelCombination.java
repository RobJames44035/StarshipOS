/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

// key: compiler.err.invalid.case.label.combination

class InvalidCaseLabelCombination {
    private void doSwitch(Integer i) {
        switch (i) {
            case null, 1: break;
            default: break;
        }
    }
}
