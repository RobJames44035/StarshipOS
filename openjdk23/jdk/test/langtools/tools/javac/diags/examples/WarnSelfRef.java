/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.warn.self.ref
// options: -XDuseBeforeDeclarationWarning

class X {
    static int x = X.x;
}
