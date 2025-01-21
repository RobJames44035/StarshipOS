/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

// key: compiler.err.feature.not.supported.in.source.plural
// key: compiler.misc.feature.var.in.try.with.resources
// options: -source 8 -Xlint:-options

class VarInTryWithResourcesNotSupportedInSource {
    void m() {
        AutoCloseable ac = new CloseableImpl();

        try (ac) {
        }
    }
}

class CloseableImpl implements AutoCloseable {
    @Override
    public void close() throws Exception {
    }
}
