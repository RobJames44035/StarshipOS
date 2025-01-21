/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

// key: compiler.err.feature.not.supported.in.source
// key: compiler.misc.feature.case.null
// options: -source 20 -Xlint:-options

class CaseNull {
    private void doSwitch(String s) {
        switch (s) {
            case null: break;
            default: break;
        }
    }
}
