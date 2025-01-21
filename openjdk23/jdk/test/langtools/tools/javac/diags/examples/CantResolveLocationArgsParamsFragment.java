/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

// key: compiler.misc.cant.resolve.location.args.params
// key: compiler.misc.location
// key: compiler.err.invalid.mref

class CantResolveLocationArgsParamsFragment {

    interface SAM {
        void m(Integer u);
    }

    void test() {
        SAM s = CantResolveLocationArgsParamsFragment::<String>f;
    }
}
