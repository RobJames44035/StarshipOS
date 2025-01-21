/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.not.within.bounds

class NotWithinBounds {

    static class Foo<X extends Number> { }

    Foo<String> f1 = null;
}
