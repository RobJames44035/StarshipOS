/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

// key: compiler.err.same.binary.name

class SameBinaryName {
    private static final class Foo$Bar {}

    private static final class Foo {
        private static final class Bar {}
    }
}
