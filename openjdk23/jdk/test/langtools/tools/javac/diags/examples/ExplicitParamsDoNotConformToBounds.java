/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.cant.apply.symbol.noargs
// key: compiler.misc.explicit.param.do.not.conform.to.bounds

class ExplicitParamsDoNotConformToBounds {
    <X extends Number> void m() {}
    { this.<String>m(); }
}
