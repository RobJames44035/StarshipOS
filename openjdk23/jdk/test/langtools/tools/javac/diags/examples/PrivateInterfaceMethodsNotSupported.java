/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

// key: compiler.err.feature.not.supported.in.source.plural
// key: compiler.misc.feature.private.intf.methods
// options: -source 8 -Xlint:-options

interface PrivateInterfaceMethodsNotSupported {
    private void foo() {}
}
