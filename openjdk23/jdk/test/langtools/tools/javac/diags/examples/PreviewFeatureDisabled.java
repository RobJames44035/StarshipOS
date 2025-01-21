/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

// key: compiler.err.preview.feature.disabled
// key: compiler.misc.feature.case.null
// options: -XDforcePreview

class PreviewFeatureDisabled {
    void m(String s) {
        switch (s) {
            case null:
        }
    }
}
