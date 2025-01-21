/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

// key: compiler.err.invalid.mref
// key: compiler.misc.mref.infer.and.explicit.params

public class MrefInferAndExplicitParams {
    static class Foo<X> {}

    interface Supplier<X> {
        X make();
    }

    Supplier<Foo<String>> sfs = Foo::<Number>new;
}
