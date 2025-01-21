/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

// key: compiler.err.feature.not.supported.in.source.plural
// key: compiler.misc.feature.pattern.switch
// options: -source 20 -Xlint:-options

class PatternSwitch {
    private void doSwitch(Object o) {
        switch (o) {
            case String str: break;
            default: break;
        }
    }
}
