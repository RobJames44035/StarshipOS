/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

// key: compiler.misc.feature.multiple.case.labels
// key: compiler.err.feature.not.supported.in.source.plural
// options: -Xlint:-options -source 13

class MultipleCaseLabels {
    void m(int i) {
        switch (i) {
            case 0, 1, 2: break;
        }
    }
}
